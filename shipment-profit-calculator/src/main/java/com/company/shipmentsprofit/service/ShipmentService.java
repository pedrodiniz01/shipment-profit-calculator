package com.company.shipmentsprofit.service;

import com.company.shipmentsprofit.dto.ProfitCalculationDto;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.mapper.ProfitMapper;
import com.company.shipmentsprofit.repository.CostRepository;
import com.company.shipmentsprofit.repository.IncomeRepository;
import com.company.shipmentsprofit.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final IncomeRepository incomeRepository;
    private final CostRepository costRepository;
    public ShipmentService(ShipmentRepository shipmentRepository,
                           IncomeRepository incomeRepository,
                           CostRepository costRepository) {
        this.shipmentRepository = shipmentRepository;
        this.incomeRepository = incomeRepository;
        this.costRepository = costRepository;
    }

    public Shipment createShipment(String referenceNumber, LocalDate shipmentDate) {
        Shipment shipment = Shipment.builder()
                .referenceNumber(referenceNumber)
                .shipmentDate(shipmentDate)
                .build();

        return shipmentRepository.save(shipment);
    }

    public Income addIncomeToShipment(Long shipmentId, String description, Double amount) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));

        Income income = Income.builder()
                .description(description)
                .amount(amount)
                .build();

        // Use the helper method in Shipment to maintain the relationship
        shipment.addIncome(income);

        // Save the shipment (which also saves the income due to cascade)
        shipmentRepository.save(shipment);

        return income;
    }

    public Cost addCostToShipment(Long shipmentId, String description, Double amount) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));

        Cost cost = Cost.builder()
                .description(description)
                .amount(amount)
                .build();

        // Use the helper method in Shipment
        shipment.addCost(cost);

        // Save shipment (cascade will handle cost)
        shipmentRepository.save(shipment);

        return cost;
    }

    public ProfitCalculationDto calculateProfit(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));

        double totalIncome = shipment.getIncomes()
                .stream()
                .mapToDouble(income -> income.getAmount() != null ? income.getAmount() : 0.0)
                .sum();

        double totalCost = shipment.getCosts()
                .stream()
                .mapToDouble(cost -> cost.getAmount() != null ? cost.getAmount() : 0.0)
                .sum();

        double profitValue = totalIncome - totalCost;

        ProfitCalculationDto dto = ProfitMapper.INSTANCE.shipmentToProfitDto(shipment);
        dto.setTotalIncome(totalIncome);
        dto.setTotalCost(totalCost);
        dto.setProfit(profitValue);

        return dto;
    }
}
