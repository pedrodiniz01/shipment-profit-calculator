package com.company.shipmentsprofit.repository;

import com.company.shipmentsprofit.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    boolean existsByReferenceNumber(String referenceNumber);
    Optional<Shipment> findByReferenceNumber(String referenceNumber);
}
