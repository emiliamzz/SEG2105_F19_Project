package com.example.seg2105_f19_project;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCases {

    @Test
    public void additionWorks() {
        int actual = 2 + 2;
        int expected = 4;
        assertEquals("Addition failed.", expected, actual);
    }

    @Test
    public void subtractionWorks() {
        int actual = 4 - 2;
        int expected = 2;
        assertEquals("Subtraction failed.", expected, actual);
    }

    @Test
    public void multiplicationWorks() {
        int actual = 2 * 2;
        int expected = 4;
        assertEquals("Multiplication failed.", expected, actual);
    }

    @Test
    public void divisionWorks() {
        int actual = 4 / 2;
        int expected = 2;
        assertEquals("Division failed.", expected, actual);
    }

    @Test
    public void exponentsWork() {
        int actual = 2 ^ 2;
        int expected = 4;
        assertEquals("Exponents failed.", expected, actual);
    }

}
