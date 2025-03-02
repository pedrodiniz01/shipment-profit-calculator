package com.company.shipmentsprofit.enums;

import java.util.Arrays;

public enum IncomeType {
    CUSTOMER_PAYMENT,
    AGENT_COMMISSION,
    REFUND,
    GOVERNMENT_SUBSIDY,
    OTHER;

    public static IncomeType fromString(String value) {
        return Arrays.stream(IncomeType.values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(OTHER);
    }
}
