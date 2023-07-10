package org.management.bank.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.management.ExceptionCustomHandler;
import org.management.exception.CustomException;
import org.management.exception.ErrorMessageConstants;
import org.management.exception.ErrorResponse;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionCustomHandlerTest {

    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("test Handle Custom Exception")
    void testHandleCustomException() {
        // Arrange
        CustomException customException = new CustomException(ErrorMessageConstants.UniqueNationalCodePerson.message,
                ErrorMessageConstants.UniqueNationalCodePerson.developer_message,
                ErrorMessageConstants.UniqueNationalCodePerson.status);

        ErrorResponse expectedResponse = customException.getErrorResponse();
        ExceptionCustomHandler exceptionHandler = new ExceptionCustomHandler();

        // Act
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleCustomException(customException);

        // Assert
        assertEquals(expectedResponse, responseEntity.getBody());
        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("test Handle Illegal Argument Exception")
    void testHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Illegal Argument Exception");
        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setErrorMsg(ErrorMessageConstants.NotNull.message);
        expectedResponse.setStatus(HttpStatus.BAD_REQUEST);
        expectedResponse.setDeveloperMsg(illegalArgumentException.getMessage());
        ExceptionCustomHandler exceptionHandler = new ExceptionCustomHandler();

        // Act
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleIllegalArgumentException(illegalArgumentException, response);

        // Assert
        assertEquals(expectedResponse, responseEntity.getBody());
        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("test Handle General Errors")
    void testHandleGeneralErrors() {
        // Arrange
        Exception exception = new Exception("General Error");
        ErrorResponse expectedResponse = new ErrorResponse();
        expectedResponse.setErrorMsg(ErrorMessageConstants.InternalServer.message);
        expectedResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        expectedResponse.setDeveloperMsg(exception.getMessage());
        ExceptionCustomHandler exceptionHandler = new ExceptionCustomHandler();

        // Act
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleGeneralErrors(exception, response);

        // Assert
        assertEquals(expectedResponse, responseEntity.getBody());
        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
    }
}
