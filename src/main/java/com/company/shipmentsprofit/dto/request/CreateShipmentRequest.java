package com.company.shipmentsprofit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateShipmentRequest {
    private String referenceNumber;
    private LocalDate shipmentDate;
}
