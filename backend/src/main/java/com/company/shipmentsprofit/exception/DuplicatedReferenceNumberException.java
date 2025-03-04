package com.company.shipmentsprofit.exception;

public class DuplicatedReferenceNumberException extends RuntimeException {
    public DuplicatedReferenceNumberException(String message) {
        super(message);
    }
}
