package org.management.bank.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.management.util.NumericStringChecker;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumericStringCheckerTest {


    @ParameterizedTest
    @MethodSource("invalidStrings")
    @DisplayName("testIsNumeric With Invalid Strings")
    void testIsNumeric_WithInvalidStrings(String sample) {

        assertEquals(false, NumericStringChecker.isNumeric(sample));

    }

    @Test
    @DisplayName("testIsNumeric With Valid String")
    void testIsNumeric_WithValidString() {
        assertEquals(true, NumericStringChecker.isNumeric("11111111111"));

    }

    private static Stream<String> invalidStrings() {
        return Stream.of(
                "5sd4",
                "sdjsdhsdjhsd",
                "454545@sdjsdhjsd"
        );
    }
}
