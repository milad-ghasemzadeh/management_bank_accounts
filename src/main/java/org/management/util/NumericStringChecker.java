package org.management.util;

public class NumericStringChecker {

    // check if the string Only just made by numbers
    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
