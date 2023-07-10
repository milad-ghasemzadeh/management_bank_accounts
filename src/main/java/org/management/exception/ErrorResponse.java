package org.management.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    private HttpStatus status;
    private String errorMsg;
    private String developerMsg;

}