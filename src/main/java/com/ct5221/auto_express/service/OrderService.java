package com.ct5221.auto_express.service;
import com.ct5221.auto_express.domain.*;
import com.ct5221.auto_express.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DealerRepository dealerRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, DealerRepository dealerRepository, VehicleRepository vehicleRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.dealerRepository = dealerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public OrderDTO createOrder(OrderDTO orderDTO){
        validateOrderDTO(orderDTO);

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderDTO.getUserId()));
        Vehicle vehicle = vehicleRepository.findById(orderDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + orderDTO.getVehicleId()));
        Dealer dealer = dealerRepository.findById(orderDTO.getDealerId())
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + orderDTO.getDealerId()));
        if(!vehicle.getAvailable()){
            throw new RuntimeException("Vehicle is not available for purchase.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setVehicle(vehicle);
        order.setDealer(dealer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(vehicle.getPrice());
        order.setStatus(OrderStatus.PROCESSING);

        vehicle.setAvailable(false);
        vehicleRepository.save(vehicle);

        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    public List<OrderDTO> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertToDTO(order);
    }

    public List<OrderDTO> getOrdersByUserId(Long userId){
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id " + userId);
        }
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByDealerId(Long dealerId){
        if (!dealerRepository.existsById(dealerId)) {
            throw new RuntimeException("Dealer not found with id " + dealerId);
        }
        List<Order> orders = orderRepository.findByUserId(dealerId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrderByStatus(OrderStatus status){
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO updateOrderStatus(Long id, OrderStatus status){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        validateStatusTransition(order.getStatus(), status);

        order.setStatus(status);

        if(status == OrderStatus.CANCELLED){
            Vehicle vehicle = order.getVehicle();
            vehicle.setAvailable(true);
            vehicleRepository.save(vehicle);
        }

        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.PROCESSING) {
            throw new IllegalStateException("Cannot delete order with status " + order.getStatus());
        }

        Vehicle vehicle = order.getVehicle();
        vehicle.setAvailable(true);
        vehicleRepository.save(vehicle);

        orderRepository.deleteById(id);
    }

    private void validateOrderDTO(OrderDTO orderDTO) {
        if (orderDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (orderDTO.getVehicleId() == null) {
            throw new IllegalArgumentException("Vehicle ID is required");
        }
        if (orderDTO.getDealerId() == null) {
            throw new IllegalArgumentException("Dealer ID is required");
        }
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == OrderStatus.COMPLETED && newStatus != OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot change status of completed order");
        }
        if (currentStatus == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change status of cancelled order");
        }
    }

    public OrderDTO convertToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setVehicleId(order.getVehicle().getId());
        dto.setDealerId(order.getDealer().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
