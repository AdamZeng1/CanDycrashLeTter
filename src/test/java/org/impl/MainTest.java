package org.impl;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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

    // ------------------ tool functions ------------------

    @Test
    void testReconstructString_basic() {
        Main main = new Main();
        String input = "aabcccbbad";
        Map<Character, List<Integer>> seq = new HashMap<>();
        seq.put('c', Arrays.asList(3, 5));
        String result = main.reconstructString(input, seq);
        assertEquals("aabbbad", result);
    }

    @Test
    void testReconstructStringWithPreviousCharacter_midChar() {
        Main main = new Main();
        String input = "aabcccbbad";
        Map<Character, List<Integer>> seq = new HashMap<>();
        seq.put('c', Arrays.asList(3, 5));
        String result = main.reconstructStringWithPreviousCharacter(input, seq, 'c');
        assertEquals("aabbbbad", result);
    }

    @Test
    void testReconstructStringWithPreviousCharacter_forA() {
        Main main = new Main();
        String input = "aaab";
        Map<Character, List<Integer>> seq = new HashMap<>();
        seq.put('a', Arrays.asList(0, 2));
        String result = main.reconstructStringWithPreviousCharacter(input, seq, 'a');
        assertEquals("b", result);  // 'a' has no previous character
    }

    @Test
    void testCheckIfThereAreNextCharacter_true() {
        Main main = new Main();
        assertTrue(main.checkIfThereAreNextCharacter("abc", 0));
    }

    @Test
    void testCheckIfThereAreNextCharacter_false() {
        Main main = new Main();
        assertFalse(main.checkIfThereAreNextCharacter("abc", 2));
    }

    @Test
    void testFindConsecutiveSequences_basic() {
        Main main = new Main();
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 6, 10);
        List<int[]> result = main.findConsecutiveSequences(list, 3);
        assertEquals(1, result.size());
        assertArrayEquals(new int[]{1, 3}, result.get(0));
    }

    @Test
    void testRemoveIntendedCharacters_basic() {
        Main main = new Main();
        Map<Character, List<Integer>> occurrence = new HashMap<>();
        occurrence.put('c', new ArrayList<>(Arrays.asList(3, 4, 5, 8)));
        Map<Character, List<Integer>> seq = new HashMap<>();
        seq.put('c', Arrays.asList(3, 5));
        main.removeIntendedCharacters('c', occurrence, seq);
        assertEquals(Arrays.asList(8), occurrence.get('c'));
    }

    @Test
    void testIndexesAfterLastRemovalMinusNumberOfRemovals() {
        Main main = new Main();
        Map<Character, List<Integer>> occurrence = new HashMap<>();
        occurrence.put('a', new ArrayList<>(Arrays.asList(1, 5, 8)));
        main.indexesAfterLastRemovalMinusNumberOfRemovals(occurrence, 2, 4);
        assertEquals(Arrays.asList(1, 3, 6), occurrence.get('a'));
    }
}
