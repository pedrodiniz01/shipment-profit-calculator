package com.company.shipmentsprofit.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateShipmentRequest {
    private String referenceNumber;
    private LocalDate shipmentDate;

}
