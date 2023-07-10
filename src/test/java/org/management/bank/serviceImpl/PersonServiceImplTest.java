package org.management.bank.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.management.domain.Person;
import org.management.dto.PersonDTO;
import org.management.exception.CustomException;
import org.management.mapper.PersonMapper;
import org.management.repository.PersonRepository;
import org.management.service.impl.PersonServiceImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonServiceImpl(personRepository, personMapper);
    }

    @Test
    @DisplayName("test Save With Valid PersonDTO")
    void testSave_WithValidPersonDTO_ReturnsSavedPersonDTO() {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Milad");
        personDTO.setNationalCode("1234567890");
        when(personRepository.existsByNationalCode(anyString())).thenReturn(false);
        when(personMapper.toEntity(personDTO)).thenReturn(new Person());
        when(personRepository.save(any(Person.class))).thenReturn(new Person());
        when(personMapper.toDto(any(Person.class))).thenReturn(personDTO);

        // Act
        PersonDTO result = personService.save(personDTO);

        // Assert
        assertNotNull(result);
        assertEquals(personDTO, result);
        verify(personRepository).existsByNationalCode(anyString());
        verify(personMapper).toEntity(personDTO);
        verify(personRepository).save(any(Person.class));
        verify(personMapper).toDto(any(Person.class));
    }

    @Test
    @DisplayName("test Save With Existing NationalCode")
    void testSave_WithExistingNationalCode_ThrowsCustomException() {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        personDTO.setNationalCode("0045455254");
        when(personRepository.existsByNationalCode(anyString())).thenReturn(true);

        // Act and Assert
        assertThrows(CustomException.class, () -> personService.save(personDTO));
    }

    @Test
    @DisplayName("test Get All Of Person")
    void testGetAllOfPerson_ReturnsPageOfPersons() {
        // Arrange
        Page<Person> persons = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(personRepository.findAll(pageable)).thenReturn(persons);
        when(persons.map(any())).thenReturn(mock(Page.class));

        // Act
        Page result = personService.getAllOfPerson(pageable);

        // Assert
        assertNotNull(result);
        verify(personRepository).findAll(pageable);
        verify(persons).map(any());
    }

}
