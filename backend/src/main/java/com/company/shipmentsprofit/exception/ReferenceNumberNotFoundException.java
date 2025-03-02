package com.company.shipmentsprofit.exception;

public class ReferenceNumberNotFoundException extends RuntimeException {
    public ReferenceNumberNotFoundException(String message) {
        super(message);
    }
}
