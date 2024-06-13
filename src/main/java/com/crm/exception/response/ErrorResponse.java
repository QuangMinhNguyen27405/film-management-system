package com.crm.exception.response;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime createdDTM;

    private ErrorType errorType;

    private int errorCode;

    private String errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime createdDTM, ErrorType errorType, int errorCode, String errorMessage) {
        this.createdDTM = createdDTM;
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedDTM() {
        return createdDTM;
    }

    public void setCreatedDTM(LocalDateTime createdDTM) {
        this.createdDTM = createdDTM;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
