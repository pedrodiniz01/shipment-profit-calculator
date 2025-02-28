package com.company.shipmentsprofit.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfitCalculationResponse {
    private String referenceNumber;
    private Double totalIncome;
    private Double totalCost;
    private Double profit;
}

