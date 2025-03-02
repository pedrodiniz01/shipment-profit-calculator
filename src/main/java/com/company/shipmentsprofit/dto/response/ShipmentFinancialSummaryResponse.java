package com.company.shipmentsprofit.dto.response;

import com.company.shipmentsprofit.enums.CostType;
import com.company.shipmentsprofit.enums.IncomeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentFinancialSummaryResponse {
    private String referenceNumber;
    private LocalDate shipmentDate;
    private Map<IncomeType, Double> incomeByCategory;
    private Map<CostType, Double> costByCategory;
}
