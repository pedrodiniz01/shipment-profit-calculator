package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.exception.TransactionException;

import java.util.Objects;

public class TransactionUtils {

    static <T> void validateShipmentAndTransaction(Shipment shipment, T transaction, Double amount) {
        Objects.requireNonNull(shipment, "Shipment can't be null");
        Objects.requireNonNull(transaction, "Transaction can't be null");

        if (amount == null || amount <= 0) {
            throw new TransactionException("Transaction value must be greater than zero.");
        }
    }
}
