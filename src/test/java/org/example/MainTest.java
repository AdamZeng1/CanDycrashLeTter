package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    // ------------------ Stage 1 ------------------

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

    // ------------------ Stage 2 ------------------

    @Test
    void testRemoveAndReplaceSingleTriplet_Stage2() {
        Main main = new Main();
        String input = "aabcccbbad";
        String sep = System.lineSeparator();
        String expectedOutput = "-> aabbbbad" + sep +
                "-> aaaad" + sep +
                "-> d";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThreeAndAddPreviousCharacter(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testNoTriplets_Stage2() {
        Main main = new Main();
        String input = "abcde";
        String expectedOutput = "";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThreeAndAddPreviousCharacter(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testAllTriplets_Stage2() {
        Main main = new Main();
        String input = "aaabbbccc";
        String sep = System.lineSeparator();
        String expectedOutput = "-> bbbccc" + sep +
                "-> accc" + sep +
                "-> ab";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThreeAndAddPreviousCharacter(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testLongNestedTriplets_Stage2() {
        Main main = new Main();
        String input = "aaabbbcccaaa";
        String sep = System.lineSeparator();
        String expectedOutput = "-> bbbcccaaa" + sep +
                "-> acccaaa" + sep +
                "-> abaaa" + sep +
                "-> ab";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThreeAndAddPreviousCharacter(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    @Test
    void testEmptyString_Stage2() {
        Main main = new Main();
        String input = "";
        String expectedOutput = "";

        String actualOutput = captureOutput(() -> main.removeCharacterEqOrGtThreeAndAddPreviousCharacter(input));
        assertEquals(expectedOutput, actualOutput.trim());
    }

    // ------------------ Shared Helper ------------------

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
