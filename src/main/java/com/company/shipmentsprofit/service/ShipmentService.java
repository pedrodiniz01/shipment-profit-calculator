package com.company.shipmentsprofit.service;

import com.company.shipmentsprofit.dto.request.AddCostRequest;
import com.company.shipmentsprofit.dto.request.AddIncomeRequest;
import com.company.shipmentsprofit.dto.response.ShipmentFinancialSummaryResponse;
import com.company.shipmentsprofit.dto.response.ShipmentSummaryResponse;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.enums.CostType;
import com.company.shipmentsprofit.enums.IncomeType;
import com.company.shipmentsprofit.exception.InvalidReferenceNumberException;
import com.company.shipmentsprofit.exception.ReferenceNumberNotFoundException;
import com.company.shipmentsprofit.mapper.Mapper;
import com.company.shipmentsprofit.repository.ShipmentRepository;
import com.company.shipmentsprofit.utils.CostUtils;
import com.company.shipmentsprofit.utils.IncomeUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private Mapper mapper;

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

    public ShipmentSummaryResponse getShipmentByReferenceNumber(String referenceNumber) {
        Shipment shipment = findShipmentByReferenceNumber(referenceNumber);
        return buildShipmentSummary(shipment);
    }

    public List<ShipmentSummaryResponse> getAllShipments() {
        return shipmentRepository.findAll()
                .stream()
                .map(this::buildShipmentSummary)
                .toList();
    }

    public Income addIncomeToShipment(String referenceNumber, AddIncomeRequest request) {
        Shipment shipment = findShipmentByReferenceNumber(referenceNumber);

        Income income = mapper.toIncome(request);
        IncomeUtils.addIncome(shipment, income);

        shipmentRepository.save(shipment);

        return income;
    }

    public Cost addCostToShipment(String referenceNumber, AddCostRequest request) {
        Shipment shipment = findShipmentByReferenceNumber(referenceNumber);

        Cost cost = mapper.toCost(request);
        CostUtils.addCost(shipment, cost);

        shipmentRepository.save(shipment);

        return cost;
    }

    public ShipmentFinancialSummaryResponse getShipmentFinancialSummary(String referenceNumber) {
        Shipment shipment = findShipmentByReferenceNumber(referenceNumber);

        Map<IncomeType, Double> incomeByCategory = IncomeUtils.calculateIncomesByCategory(shipment);
        Map<CostType, Double> costByCategory = CostUtils.calculateCostsByCategory(shipment);

        return ShipmentFinancialSummaryResponse.builder()
                .referenceNumber(shipment.getReferenceNumber())
                .shipmentDate(shipment.getShipmentDate())
                .incomeByCategory(incomeByCategory)
                .costByCategory(costByCategory)
                .build();
    }

    private ShipmentSummaryResponse buildShipmentSummary(Shipment shipment) {
        double totalIncome = IncomeUtils.calculateTotalIncome(shipment);
        double totalCost = CostUtils.calculateTotalCost(shipment);

        double netAmount = totalIncome - totalCost;
        boolean isProfit = netAmount > 0;

        return ShipmentSummaryResponse.builder()
                .referenceNumber(shipment.getReferenceNumber())
                .shipmentDate(shipment.getShipmentDate())
                .totalIncome(totalIncome)
                .totalCost(totalCost)
                .netAmount(netAmount)
                .isProfit(isProfit)
                .build();
    }

    private Shipment findShipmentByReferenceNumber(String referenceNumber) {
        return shipmentRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new ReferenceNumberNotFoundException(String.format("Shipment not found: %s.", referenceNumber)));
    }
}
