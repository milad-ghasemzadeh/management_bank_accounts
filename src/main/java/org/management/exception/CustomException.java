package org.management.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private ErrorResponse errorResponse;

    @Autowired
    public CustomException(String message, String developerMessage, HttpStatus status) {
        super(message);

        errorResponse = new ErrorResponse();
        errorResponse.setDeveloperMsg(developerMessage);
        errorResponse.setErrorMsg(message);
        errorResponse.setStatus(status);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}