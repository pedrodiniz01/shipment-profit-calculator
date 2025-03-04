package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.entity.Shipment;
import com.company.shipmentsprofit.exception.InvalidInputException;
import com.company.shipmentsprofit.exception.TransactionAmountException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Objects;

public class ValidationUtils {

    public static void validateCreateShipment(String referenceNumber, LocalDate shipmentDate) {
        if (Strings.isBlank(referenceNumber)) {
            throw new InvalidInputException("Reference number can't be null or empty.");
        }
        if (ObjectUtils.isEmpty(shipmentDate)) {
            throw new InvalidInputException("Description can't be null or empty.");
        }
    }

    static <T> void validateShipmentAndTransaction(Shipment shipment, T transaction, Double amount) {
        Objects.requireNonNull(shipment, "Shipment can't be null");
        Objects.requireNonNull(transaction, "Transaction can't be null");

        if (amount == null || amount <= 0) {
            throw new TransactionAmountException("Transaction value must be greater than zero.");
        }
    }

}
