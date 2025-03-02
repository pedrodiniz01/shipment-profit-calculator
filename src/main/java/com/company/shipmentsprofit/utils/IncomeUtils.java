package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;

public class IncomeUtils {
    public static void addIncome(Shipment shipment, Income income) {
        TransactionUtils.validateShipmentAndTransaction(shipment, income, income.getAmount());
        shipment.getIncomes().add(income);
        income.setShipment(shipment);
    }
    public static double calculateTotalIncome(Shipment shipment) {
        return shipment.getIncomes()
                .stream()
                .mapToDouble(income -> income.getAmount() != null ? income.getAmount() : 0.0)
                .sum();
    }
}
