package com.company.shipmentsprofit.controller;

import com.company.shipmentsprofit.dto.AddCostRequest;
import com.company.shipmentsprofit.dto.AddIncomeRequest;
import com.company.shipmentsprofit.dto.CreateShipmentRequest;
import com.company.shipmentsprofit.dto.ProfitCalculationDto;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/{shipmentId}/profit")
    public ResponseEntity<ProfitCalculationDto> calculateProfit(@PathVariable Long shipmentId) {
        ProfitCalculationDto result = shipmentService.calculateProfit(shipmentId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity<Shipment> createShipment(@RequestBody CreateShipmentRequest request) {
        Shipment shipment = shipmentService.createShipment(
                request.getReferenceNumber(),
                request.getShipmentDate()
        );
        return ResponseEntity.ok(shipment);
    }

    @PostMapping("/{shipmentId}/incomes")
    public ResponseEntity<Income> addIncome(@PathVariable Long shipmentId,
                                            @RequestBody AddIncomeRequest request) {
        Income income = shipmentService.addIncomeToShipment(
                shipmentId,
                request.getDescription(),
                request.getAmount()
        );
        return ResponseEntity.ok(income);
    }

    @PostMapping("/{shipmentId}/costs")
    public ResponseEntity<Cost> addCost(@PathVariable Long shipmentId,
                                        @RequestBody AddCostRequest request) {
        Cost cost = shipmentService.addCostToShipment(
                shipmentId,
                request.getDescription(),
                request.getAmount()
        );
        return ResponseEntity.ok(cost);
    }
}
