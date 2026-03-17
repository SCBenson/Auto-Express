package com.ct5221.auto_express.dto;

import com.ct5221.auto_express.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private Long dealerId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private OrderStatus status;

    public OrderDTO(){}

    public OrderDTO(Long id, Long userId, Long vehicleId, Long dealerId, LocalDateTime orderDate, BigDecimal totalPrice, OrderStatus status){
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.dealerId = dealerId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


}
