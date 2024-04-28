package com.rangers.medicineservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@RequiredArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_detail_id")
    UUID orderDetailId;

    @OneToOne
    @Column(name = "medicine_id")
    Medicine medicine;

    @Column(name = "quantity")
    Integer quantity;
}
