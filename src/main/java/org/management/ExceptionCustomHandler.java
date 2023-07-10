package org.management;

import org.management.exception.CustomException;
import org.management.exception.ErrorMessageConstants;
import org.management.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionCustomHandler {

    // handle CustomExceptions
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getErrorResponse(), e.getErrorResponse().getStatus());
    }

    // handle IllegalArgumentExceptions
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e, HttpServletResponse response) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMsg(ErrorMessageConstants.NotNull.message);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setDeveloperMsg(e.getMessage());
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    // handle General Exceptions
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralErrors(Exception e, HttpServletResponse response) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMsg(ErrorMessageConstants.InternalServer.message);
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setDeveloperMsg(e.getMessage());
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());

    }

}