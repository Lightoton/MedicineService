package com.rangers.medicineservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_cost")
    private BigDecimal orderCost;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id")
    @JsonIgnore
    private Pharmacy pharmacy;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id", referencedColumnName = "prescription_id")
    @JsonIgnore
    private Prescription prescription;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && status == order.status && Objects.equals(orderCost, order.orderCost) && Objects.equals(deliveryAddress, order.deliveryAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status, orderCost, deliveryAddress);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", status=" + status +
                ", orderCost=" + orderCost +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", pharmacy=" + pharmacy +
                ", user=" + user +
                ", prescription=" + prescription +
                ", orderDetails=" + orderDetails +
                ", orderDate=" + orderDate +
                '}';
    }
}
