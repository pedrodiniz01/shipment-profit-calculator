package com.company.shipmentsprofit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfitCalculationDto {
    private Long shipmentId;
    private Double totalIncome;
    private Double totalCost;
    private Double profit;
}

