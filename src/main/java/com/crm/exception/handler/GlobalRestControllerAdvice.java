package com.crm.exception.handler;

import com.crm.exception.custom.DuplicateEmailException;
import com.crm.exception.custom.RecordNotFoundException;
import com.crm.exception.response.ErrorResponse;
import com.crm.exception.response.ErrorType;
import com.crm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @Autowired
    private DateUtils dateUtils;

    @ExceptionHandler(RecordNotFoundException.class)
    private ErrorResponse handleRecordNotFoundException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCreatedDTM(dateUtils.getLocalDateTime());
        errorResponse.setErrorType(ErrorType.SERVICE_EXCEPTION);
        errorResponse.setErrorCode(HttpStatus.NO_CONTENT.value());
        errorResponse.setErrorMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(DuplicateEmailException.class)
    private ErrorResponse handleDuplicateEmailException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCreatedDTM(dateUtils.getLocalDateTime());
        errorResponse.setErrorType(ErrorType.SERVICE_EXCEPTION);
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setErrorMessage(ex.getMessage());
        return errorResponse;
    }
}
