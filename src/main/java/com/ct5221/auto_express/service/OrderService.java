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
        order.setStatus(OrderStatus.PENDING);

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
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByDealerId(Long dealerId){
        List<Order> orders = orderRepository.findByDealerId(dealerId);
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

    public OrderDTO updateOrderStatus(Long id, OrderStatus newStatus){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        OrderStatus oldStatus = order.getStatus();
        order.setStatus(newStatus);

        if(newStatus == OrderStatus.CANCELLED && oldStatus != OrderStatus.CANCELLED){
            Vehicle vehicle = order.getVehicle();
            vehicle.setAvailable(true);
            vehicleRepository.save(vehicle);
        }

        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        Vehicle vehicle = order.getVehicle();
        vehicle.setAvailable(true);
        vehicleRepository.save(vehicle);

        orderRepository.delete(order);
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
