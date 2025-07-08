package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void testRemoveSingleTriplet() {
        Main main = new Main();
        String input = "aabcccbbad";
        String sep = System.lineSeparator();
        String expectedOutput = "-> aabbbad" + sep +
                "-> aaad" + sep +
                "-> d";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThree(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testNoTriplets() {
        Main main = new Main();
        String input = "abcde";
        String expectedOutput = "";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThree(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testAllTriplets() {
        Main main = new Main();
        String input = "aaabbbccc";
        String sep = System.lineSeparator();
        String expectedOutput = "-> bbbccc" + sep +
                "-> ccc" + sep +
                "->";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThree(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testLongNestedTriplets() {
        Main main = new Main();
        String input = "aaabbbcccaaa";
        String sep = System.lineSeparator();
        String expectedOutput = "-> bbbcccaaa" + sep +
                "-> cccaaa" + sep +
                "-> aaa" + sep +
                "->";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThree(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testEmptyString() {
        Main main = new Main();
        String input = "";
        String expectedOutput = "";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThree(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    // Helper to capture System.out.print output
    private String captureOutput(Runnable task) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            task.run();
        } finally {
            System.setOut(originalOut);
        }

        return outputStream.toString().trim();
    }
}
