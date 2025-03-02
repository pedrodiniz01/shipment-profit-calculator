package com.company.shipmentsprofit.dto.request;

import com.company.shipmentsprofit.enums.IncomeType;
import lombok.Getter;

@Getter
public class AddIncomeRequest {
    private String type;
    private Double amount;

    public IncomeType getIncomeType() {
        return IncomeType.fromString(type);
    }
}
