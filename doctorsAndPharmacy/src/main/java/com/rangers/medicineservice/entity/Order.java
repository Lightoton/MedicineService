package com.rangers.medicineservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    UUID ordeId;

    @Column(name = "user_id")
    User user;

    @Column(name = "recept_id")
    Prescription prescription;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    List<OrderDetails> orderDetails;

    @Column(name = "order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(name = "order_cost")
    BigDecimal orderCost;

    @Column(name = "delivery_address")
    String deliveryAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(ordeId, order.ordeId) && Objects.equals(user, order.user) && Objects.equals(prescription, order.prescription) && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordeId, user, prescription, orderDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "ordeId=" + ordeId +
                ", orderDetails=" + orderDetails +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", orderCost=" + orderCost +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }
}
