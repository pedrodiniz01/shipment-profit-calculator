package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Cost;
import com.company.shipmentsprofit.entity.Income;
import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.exception.TransactionException;

import java.util.Objects;

public class TransactionUtils {

    public static void addIncome(Shipment shipment, Income income) {
        validateShipmentAndTransaction(shipment, income, income.getAmount());
        shipment.getIncomes().add(income);
        income.setShipment(shipment);
    }

    public static void addCost(Shipment shipment, Cost cost) {
        validateShipmentAndTransaction(shipment, cost, cost.getAmount());
        shipment.getCosts().add(cost);
        cost.setShipment(shipment);
    }
    private static <T> void validateShipmentAndTransaction(Shipment shipment, T transaction, Double amount) {
        Objects.requireNonNull(shipment, "Shipment can't be null");
        Objects.requireNonNull(transaction, "Transaction can't be null");

        if (amount == null || amount <= 0) {
            throw new TransactionException("Transaction value must be greater than zero.");
        }
    }
}
