package com.company.shipmentsprofit.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
}
