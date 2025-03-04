package com.company.shipmentsprofit.exception.handler;

import com.company.shipmentsprofit.constants.Constants;
import com.company.shipmentsprofit.dto.error.ErrorResponse;
import com.company.shipmentsprofit.exception.InvalidInputException;
import com.company.shipmentsprofit.exception.DuplicatedReferenceNumberException;
import com.company.shipmentsprofit.exception.ReferenceNumberNotFoundException;
import com.company.shipmentsprofit.exception.TransactionAmountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Constants.Exceptions.INVALID_INPUT_EXCEPTION, ex.getMessage()));
    }

    @ExceptionHandler(DuplicatedReferenceNumberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedReferenceNumberException(DuplicatedReferenceNumberException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Constants.Exceptions.DUPLICATED_REFERENCE_NUMBER_EXCEPTION, ex.getMessage()));
    }

    @ExceptionHandler(ReferenceNumberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReferenceNumberNotFoundException(ReferenceNumberNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TransactionAmountException.class)
    public ResponseEntity<ErrorResponse> handleTransactionException(TransactionAmountException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Constants.Exceptions.TRANSACTION_AMOUNT_NOT_VALID, ex.getMessage()));
    }
}

