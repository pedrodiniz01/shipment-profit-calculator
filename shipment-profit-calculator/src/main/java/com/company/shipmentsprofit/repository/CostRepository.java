package com.company.shipmentsprofit.repository;

import com.company.shipmentsprofit.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost, Long> {
}
