package com.company.shipmentsprofit.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentSummaryResponse {
    private String referenceNumber;
    private LocalDate shipmentDate;
    private Double totalIncome;
    private Double totalCost;
    private Double netAmount;
    private Boolean isProfit;
}

