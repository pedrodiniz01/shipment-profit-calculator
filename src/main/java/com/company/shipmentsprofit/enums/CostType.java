package com.company.shipmentsprofit.enums;

import java.util.Arrays;

public enum CostType {
    FUEL,
    LABOR,
    TAXES,
    MAINTENANCE,
    INSURANCE,
    HANDLING,
    OTHER;

    public static CostType fromString(String value) {
        return Arrays.stream(CostType.values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(OTHER);
    }
}