package com.company.shipmentsprofit.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceNumber;

    private LocalDate shipmentDate;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cost> costs = new ArrayList<>();

    public void addIncome(Income income) {
        this.incomes.add(income);
        income.setShipment(this);
    }
    public void removeIncome(Income income) {
        this.incomes.remove(income);
        income.setShipment(null);
    }

    public void addCost(Cost cost) {
        this.costs.add(cost);
        cost.setShipment(this);
    }
    public void removeCost(Cost cost) {
        this.costs.remove(cost);
        cost.setShipment(null);
    }
}
