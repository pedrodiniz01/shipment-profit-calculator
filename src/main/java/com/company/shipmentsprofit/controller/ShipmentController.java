package com.company.shipmentsprofit.controller;

import com.company.shipmentsprofit.dto.request.AddCostRequest;
import com.company.shipmentsprofit.dto.request.AddIncomeRequest;
import com.company.shipmentsprofit.dto.request.CreateShipmentRequest;
import com.company.shipmentsprofit.dto.response.ProfitCalculationResponse;
import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.exception.ErrorResponse;
import com.company.shipmentsprofit.exception.InvalidReferenceNumberException;
import com.company.shipmentsprofit.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createShipment(@RequestBody CreateShipmentRequest request) {
        try {
            Shipment shipment = shipmentService.createShipment(request.getReferenceNumber(), request.getShipmentDate());

            return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
        } catch (InvalidReferenceNumberException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_REFERENCE_NUMBER", ex.getMessage()));
        }
    }

    @GetMapping("/{referenceNumber}")
    public ResponseEntity<Shipment> getShipmentByReferenceNumber(@PathVariable String referenceNumber) {
        Shipment shipment = shipmentService.getShipmentByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(shipment);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @PostMapping("/{referenceNumber}/income")
    public ResponseEntity<Income> addIncome(@PathVariable String referenceNumber,
                                            @RequestBody AddIncomeRequest request) {
        Income income = shipmentService.addIncomeToShipment(
                referenceNumber,
                request.getDescription(),
                request.getAmount()
        );
        return ResponseEntity.ok(income);
    }

    @PostMapping("/{referenceNumber}/costs")
    public ResponseEntity<Cost> addCost(@PathVariable String referenceNumber,
                                        @RequestBody AddCostRequest request) {
        Cost cost = shipmentService.addCostToShipment(
                referenceNumber,
                request.getDescription(),
                request.getAmount()
        );
        return ResponseEntity.ok(cost);
    }

    @GetMapping("/{referenceNumber}/profit")
    public ResponseEntity<ProfitCalculationResponse> calculateProfit(@PathVariable String referenceNumber) {
        ProfitCalculationResponse result = shipmentService.calculateProfit(referenceNumber);
        return ResponseEntity.ok(result);
    }
}
