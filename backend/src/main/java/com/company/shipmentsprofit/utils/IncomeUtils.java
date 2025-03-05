package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.enums.IncomeType;

import java.util.Map;
import java.util.stream.Collectors;

public class IncomeUtils {
    public static void addIncome(Shipment shipment, Income income) {
        shipment.getIncomes().add(income);
        income.setShipment(shipment);
    }

    public static double calculateTotalIncome(Shipment shipment) {
        return shipment.getIncomes()
                .stream()
                .mapToDouble(income -> income.getAmount() != null ? income.getAmount() : 0.0)
                .sum();
    }

    public static Map<IncomeType, Double> calculateIncomesByCategory(Shipment shipment) {
        return shipment.getIncomes()
                .stream()
                .collect(Collectors.groupingBy(
                        income -> IncomeType.fromString(income.getDescription()),
                        Collectors.summingDouble(income -> income.getAmount() != null ? income.getAmount() : 0.0)
                ));
    }
}
