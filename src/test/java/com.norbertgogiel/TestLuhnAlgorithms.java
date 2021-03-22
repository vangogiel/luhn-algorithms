package com.norbertgogiel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class TestLuhnAlgorithms {

    @Test
    public void testEvenLengthLongIsValid() {
        assertTrue(LuhnAlgorithms.isValid(4716881992809921L));
    }

    @Test
    public void testOddLengthLongIsValid() {
        assertTrue(LuhnAlgorithms.isValid(372095199917337L));
    }

    @Test
    public void testLongIsNotValid() {
        assertFalse(LuhnAlgorithms.isValid(4444333322221112L));
    }

    @Test
    public void testStringIsValid() {
        assertTrue(LuhnAlgorithms.isValid("4716881992809921"));
    }

    @Test
    public void testStringIsNotValid() {
        assertFalse(LuhnAlgorithms.isValid("4444333322221112"));
    }

    @Test
    public void testStringBiggerThanMaxLongAndThrows() {
        assertThrows(NumberFormatException.class, () -> LuhnAlgorithms.isValid("9223372036854775808"));
    }

    @Test
    public void testStringContainsNotNumberASCIIAndThrows() {
        assertThrows(NumberFormatException.class, () -> LuhnAlgorithms.isValid("a/+@!"));
    }

    @Test
    public void testGenerateOddLengthLuhnNumber() {
        long testNumber = LuhnAlgorithms.generateLuhnFromRange(111,222, 5);
        assertTrue(LuhnAlgorithms.isValid(testNumber));
    }

    @Test
    public void testGenerateEvenLengthLuhnNumber() {
        long testNumber = LuhnAlgorithms.generateLuhnFromRange(111,222, 6);
        assertTrue(LuhnAlgorithms.isValid(testNumber));
    }
}
