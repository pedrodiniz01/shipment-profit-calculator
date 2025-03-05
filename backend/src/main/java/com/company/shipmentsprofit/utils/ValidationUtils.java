package com.company.shipmentsprofit.utils;

import com.company.shipmentsprofit.dto.request.TransactionAmount;
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
            throw new InvalidInputException("Shipment Date can't be null or empty.");
        }
    }

    public static void validateTransactionAmount(TransactionAmount transactionAmount) {
        if (Objects.isNull(transactionAmount) || Objects.isNull(transactionAmount.getAmount()) || transactionAmount.getAmount() <= 0) {
            throw new TransactionAmountException("Transaction amount can't be null.");
        }
    }
}
