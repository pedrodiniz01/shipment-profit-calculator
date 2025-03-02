package com.company.shipmentsprofit.service;

import com.company.shipmentsprofit.dto.response.ProfitCalculationResponse;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.exception.InvalidReferenceNumberException;
import com.company.shipmentsprofit.exception.ReferenceNumberNotFoundException;
import com.company.shipmentsprofit.repository.ShipmentRepository;
import com.company.shipmentsprofit.utils.TransactionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

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
        return findShipmentByReferenceNumber(referenceNumber);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Income addIncomeToShipment(String referenceNumber, String description, Double amount) {
        Shipment shipment = findShipmentByReferenceNumber(referenceNumber);

        Income income = Income.builder()
                .description(description)
                .amount(amount)
                .build();

        TransactionUtils.addIncome(shipment, income);
        shipmentRepository.save(shipment);

        return income;
    }

    public Cost addCostToShipment(String referenceNumber, String description, Double amount) {
        Shipment shipment = findShipmentByReferenceNumber(referenceNumber);

        Cost cost = Cost.builder()
                .description(description)
                .amount(amount)
                .build();

        TransactionUtils.addCost(shipment, cost);
        shipmentRepository.save(shipment);

        return cost;
    }

    public ProfitCalculationResponse calculateProfit(String referenceNumber) {
        Shipment shipment = findShipmentByReferenceNumber(referenceNumber);

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

    private Shipment findShipmentByReferenceNumber(String referenceNumber) {
        return shipmentRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new ReferenceNumberNotFoundException(String.format("Shipment not found: %s.", referenceNumber)));
    }
}
