package com.company.shipmentsprofit.dto.request;

import com.company.shipmentsprofit.enums.CostType;
import lombok.Getter;

@Getter
public class AddCostRequest {
    private String type;
    private Double amount;

    public CostType getCostType() {
        return CostType.fromString(type);
    }
}
