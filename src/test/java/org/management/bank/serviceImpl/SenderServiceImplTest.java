package org.management.bank.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.management.domain.Sender;
import org.management.dto.SenderDTO;
import org.management.mapper.SenderMapper;
import org.management.repository.SenderRepository;
import org.management.service.impl.SenderServiceImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SenderServiceImplTest {

    @Mock
    private SenderRepository senderRepository;

    @Mock
    private SenderMapper senderMapper;


    private SenderServiceImpl senderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        senderService = new SenderServiceImpl(senderRepository, senderMapper);
    }

    @Test
    @DisplayName("test Get All Of Senders")
    void testGetAllOfSenders_ReturnsListOfSenders() {
        // Arrange
        List<Sender> senders = new ArrayList<>();
        senders.add(new Sender());

        when(senderRepository.findAll()).thenReturn(senders);
        when(senderMapper.toDto(any(Sender.class))).thenReturn(new SenderDTO());

        // Act
        List<SenderDTO> result = senderService.getAllOfSenders();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(senderRepository).findAll();
        verify(senderMapper, times(senders.size())).toDto(any(Sender.class));
    }

}
