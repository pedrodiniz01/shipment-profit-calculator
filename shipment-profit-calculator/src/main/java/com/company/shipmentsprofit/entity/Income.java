package com.company.shipmentsprofit.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
}
