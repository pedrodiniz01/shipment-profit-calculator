package com.company.shipmentsprofit.repository;

import com.company.shipmentsprofit.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
