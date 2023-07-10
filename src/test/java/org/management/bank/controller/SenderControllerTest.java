package org.management.bank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.management.controller.SenderController;
import org.management.dto.SenderDTO;
import org.management.service.SenderService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SenderControllerTest {

    @Mock
    private SenderService senderService;


    private SenderController senderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        senderController = new SenderController(senderService);
    }

    @Test
    @DisplayName("test Get All Of Senders")
    void testGetAllOfSenders_ReturnsListOfSenders() {
        // Arrange
        List<SenderDTO> senders = new ArrayList<>();
        senders.add(new SenderDTO());

        when(senderService.getAllOfSenders()).thenReturn(senders);

        // Act
        ResponseEntity<List<SenderDTO>> response = senderController.getAllOfSenders();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(senders, response.getBody());
        verify(senderService).getAllOfSenders();
    }

}
