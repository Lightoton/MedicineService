package com.rangers.medicineservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_detail_id")
    private UUID orderDetailId;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    private Medicine medicine;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @JsonIgnore
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(orderDetailId, that.orderDetailId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetailId, quantity);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId=" + orderDetailId +
                ", quantity=" + quantity +
                ", medicine=" + medicine +
                '}';
    }
}
