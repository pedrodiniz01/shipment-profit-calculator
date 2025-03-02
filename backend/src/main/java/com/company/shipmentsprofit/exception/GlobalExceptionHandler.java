package com.company.shipmentsprofit.exception;

import com.company.shipmentsprofit.constants.Constants;
import com.company.shipmentsprofit.dto.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidReferenceNumberException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReferenceNumberException(InvalidReferenceNumberException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Constants.Exceptions.INVALID_REFERENCE_NUMBER, ex.getMessage()));
    }

    @ExceptionHandler(ReferenceNumberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReferenceNumberNotFoundException(ReferenceNumberNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Constants.Exceptions.REFERENCE_NUMBER_NOT_FOUND, ex.getMessage()));
    }
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleTransactionException(TransactionException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Constants.Exceptions.TRANSACTION_NOT_VALUE, ex.getMessage()));
    }
}

