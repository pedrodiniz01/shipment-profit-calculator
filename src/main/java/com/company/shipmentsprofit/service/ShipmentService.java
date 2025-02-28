package com.company.shipmentsprofit.service;

import com.company.shipmentsprofit.dto.response.ProfitCalculationResponse;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.exception.InvalidReferenceNumberException;
import com.company.shipmentsprofit.repository.CostRepository;
import com.company.shipmentsprofit.repository.IncomeRepository;
import com.company.shipmentsprofit.repository.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

        if (shipmentRepository.existsByReferenceNumber(referenceNumber)) {
            throw new InvalidReferenceNumberException("Shipment with reference number " + referenceNumber + " already exists.");
        }

        Shipment shipment = Shipment.builder()
                .referenceNumber(referenceNumber)
                .shipmentDate(shipmentDate)
                .build();

        return shipmentRepository.save(shipment);
    }

    public Shipment getShipmentByReferenceNumber(String referenceNumber) {
        return shipmentRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + referenceNumber));
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Income addIncomeToShipment(String referenceNumber, String description, Double amount) {
        Shipment shipment = shipmentRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + referenceNumber));

        Income income = Income.builder()
                .description(description)
                .amount(amount)
                .build();

        shipment.addIncome(income);

        shipmentRepository.save(shipment);

        return income;
    }

    public Cost addCostToShipment(String referenceNumber, String description, Double amount) {
        Shipment shipment = shipmentRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + referenceNumber));

        Cost cost = Cost.builder()
                .description(description)
                .amount(amount)
                .build();

        shipment.addCost(cost);

        shipmentRepository.save(shipment);

        return cost;
    }

    public ProfitCalculationResponse calculateProfit(String referenceNumber) {
        Shipment shipment = shipmentRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + referenceNumber));

        double totalIncome = shipment.getIncomes()
                .stream()
                .mapToDouble(income -> income.getAmount() != null ? income.getAmount() : 0.0)
                .sum();

        double totalCost = shipment.getCosts()
                .stream()
                .mapToDouble(cost -> cost.getAmount() != null ? cost.getAmount() : 0.0)
                .sum();

        double profitValue = totalIncome - totalCost;


        ProfitCalculationResponse dto = ProfitCalculationResponse.builder()
                .referenceNumber(referenceNumber)
                .totalIncome(totalIncome)
                .totalCost(totalCost)
                .profit(profitValue).build();

        return dto;
    }
}
