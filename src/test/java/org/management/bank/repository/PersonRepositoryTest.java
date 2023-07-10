package org.management.bank.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.management.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("test ExistsByNationalCode With Existing NationalCode")
    void testExistsByNationalCode_WithExistingNationalCode_ReturnsTrue() {
        // Arrange
        String nationalCode = "9654587454";

        // Act
        boolean exists = personRepository.existsByNationalCode(nationalCode);

        // Assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("test ExistsByNationalCode With Non Existing NationalCode")
    void testExistsByNationalCode_WithNonExistingNationalCode_ReturnsFalse() {

        // Arrange
        String nationalCode = "12";

        // Act
        boolean exists = personRepository.existsByNationalCode(nationalCode);

        // Assert
        assertFalse(exists);
    }

}
