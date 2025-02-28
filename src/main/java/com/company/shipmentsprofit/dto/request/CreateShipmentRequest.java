package com.company.shipmentsprofit.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateShipmentRequest {
    private String referenceNumber;
    private LocalDate shipmentDate;
}
