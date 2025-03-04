package com.company.shipmentsprofit.controller;

import com.company.shipmentsprofit.dto.request.AddCostRequest;
import com.company.shipmentsprofit.dto.request.AddIncomeRequest;
import com.company.shipmentsprofit.dto.request.CreateShipmentRequest;
import com.company.shipmentsprofit.dto.response.ShipmentFinancialSummaryResponse;
import com.company.shipmentsprofit.dto.response.ShipmentSummaryResponse;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping(path = "/create")
    public ResponseEntity<Shipment> createShipment(@RequestBody CreateShipmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.createShipment(request.getReferenceNumber(), request.getShipmentDate()));
    }

    @GetMapping("/{referenceNumber}")
    public ResponseEntity<ShipmentSummaryResponse> getShipmentByReferenceNumber(@PathVariable String referenceNumber) {
        return ResponseEntity.ok(shipmentService.getShipmentByReferenceNumber(referenceNumber));
    }

    @GetMapping("/{referenceNumber}/financial-summary")
    public ResponseEntity<ShipmentFinancialSummaryResponse> getFinancialSummaryByCategory(@PathVariable String referenceNumber) {
        return ResponseEntity.ok(shipmentService.getFinancialSummaryByCategory(referenceNumber));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentSummaryResponse>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @PostMapping("/{referenceNumber}/income")
    public ResponseEntity<Income> addIncome(@PathVariable String referenceNumber, @RequestBody AddIncomeRequest request) {
        return ResponseEntity.ok(shipmentService.addIncomeToShipment(referenceNumber, request));
    }

    @PostMapping("/{referenceNumber}/costs")
    public ResponseEntity<Cost> addCost(@PathVariable String referenceNumber, @RequestBody AddCostRequest request) {
        return ResponseEntity.ok(shipmentService.addCostToShipment(referenceNumber, request));
    }
}
