package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.removeCharacterEqOrGtThreeAndAddPreviousCharacter("abcccbad");
    }

    public void removeCharacterEqOrGtThreeAndAddPreviousCharacter(String inputString) {
        Map<Character, List<Integer>> characterOccurrence = new HashMap<>();
        for (int i = 0; i < inputString.length(); i++) {
            Character character = inputString.charAt(i);
            if(!characterOccurrence.containsKey(character)) {
                characterOccurrence.put(character, new ArrayList<>(List.of(i)));
            } else {
                List<Integer> indexes = characterOccurrence.get(character);
                indexes.add(i);
            }

            if(checkIfThereAreNextCharacter(inputString, i)) {
                while(checkIfThereAreNextCharacter(inputString, i) &&
                        character==inputString.charAt(i+1)) {
                    List<Integer> indexes = characterOccurrence.get(character);
                    indexes.add(i+1);
                    i++;
                }
            }

            Map<Character, List<Integer>> potentialSequence = checkIfMoreThanThreeCharacterSequence(characterOccurrence);
            if(!potentialSequence.isEmpty()) { // have sequence
                int numberOfRemovals = removeCharacterEqOrGtThree(inputString,
                        potentialSequence,
                        character,
                        characterOccurrence);
                String newInputString = reconstructStringWithPreviousCharacter(inputString, potentialSequence,character);
                System.out.println("-> " + newInputString);
                inputString = newInputString;
                i = i - numberOfRemovals;
            }
            // before checking, check if it is the end of the string
            // if it is, check if there are three characters in sequence after adding to the map
            // if it is not, checking if next character is the same as current iterating character
            // if it is, keep putting in the character into the map
            // if it is not, check if there are three characters or more in sequence after adding to the map
            // if there are, create a new string which length equals original minus number of sequential characters.
            // after removal, check the all index after the last index of removed characters and minus number of removed characters
            // iterating the map according to a b c, reconstruct the string and output.
            // reassign the input string and relocate the index
        }
    }

    public void removeCharacterEqOrGtThree(String inputString) {
        Map<Character, List<Integer>> characterOccurrence = new HashMap<>();
        for (int i = 0; i < inputString.length(); i++) {
            Character character = inputString.charAt(i);
            if(!characterOccurrence.containsKey(character)) {
                characterOccurrence.put(character, new ArrayList<>(List.of(i)));
            } else {
                List<Integer> indexes = characterOccurrence.get(character);
                indexes.add(i);
            }

            if(checkIfThereAreNextCharacter(inputString, i)) {
                while(checkIfThereAreNextCharacter(inputString, i) &&
                        character==inputString.charAt(i+1)) {
                    List<Integer> indexes = characterOccurrence.get(character);
                    indexes.add(i+1);
                    i++;
                }
            }

            Map<Character, List<Integer>> potentialSequence = checkIfMoreThanThreeCharacterSequence(characterOccurrence);
            if(!potentialSequence.isEmpty()) { // have sequence
                int numberOfRemovals = removeCharacterEqOrGtThree(inputString,
                        potentialSequence,
                        character,
                        characterOccurrence);
                String newInputString = reconstructString(inputString, potentialSequence);
                System.out.println("-> " + newInputString);
                inputString = newInputString;
                i = i - numberOfRemovals;
            }
            // before checking, check if it is the end of the string
            // if it is, check if there are three characters in sequence after adding to the map
            // if it is not, checking if next character is the same as current iterating character
            // if it is, keep putting in the character into the map
            // if it is not, check if there are three characters or more in sequence after adding to the map
            // if there are, create a new string which length equals original minus number of sequential characters.
            // after removal, check the all index after the last index of removed characters and minus number of removed characters
            // iterating the map according to a b c, reconstruct the string and output.
            // reassign the input string and relocate the index
        }
    }


    // do we need this?
    public void addPreviousCharacter(Character character,
                                     Map<Character, List<Integer>> characterOccurrence,
                                     Map<Character, List<Integer>> intendedSequence) {
        List<Integer> indexes = characterOccurrence.get(character + 1);
        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i) > intendedSequence.get(character).getFirst()) {
                indexes.add(i, intendedSequence.get(character).getFirst());
            }
        }
    }

    public int removeCharacterEqOrGtThree(String inputString,
                                           Map<Character, List<Integer>> intendedSequence,
                                           Character character,
                                           Map<Character, List<Integer>> characterOccurrence) {
        int numberOfRemovals = intendedSequence.get(character).getLast() - intendedSequence.get(character).getFirst() + 1;
        int lengthOfNewString = inputString.length() - numberOfRemovals;
        indexesAfterLastRemovalMinusNumberOfRemovals(characterOccurrence,
                                                    numberOfRemovals,
                                                    lengthOfNewString);
        removeIntendedCharacters(character,characterOccurrence,intendedSequence);
        return numberOfRemovals;
    }

    public String reconstructStringWithPreviousCharacter(String inputString,
                                    Map<Character, List<Integer>> potentialSequence,
                                                         Character character) {
        if (potentialSequence.isEmpty()) return inputString;

        Map.Entry<Character, List<Integer>> entry = potentialSequence.entrySet().iterator().next();
        List<Integer> range = entry.getValue();

        if (range.size() != 2) {
            throw new IllegalArgumentException("Index range must contain exactly two elements.");
        }

        int start = range.get(0); // lower bound
        int end = range.get(1);   // upper bound

        if (start > end || start < 0 || end >= inputString.length()) {
            throw new IndexOutOfBoundsException("Invalid range: " + start + " to " + end);
        }

        Character previousCharacter = character == 'a'? '\0': (char)(character - 1);
        String newString;
        if(character == 'a') {
            newString = inputString.substring(0, start) + inputString.substring(end + 1);
        } else {
            newString = inputString.substring(0, start) + previousCharacter + inputString.substring(end + 1);
        }
        return newString;
    }

    public String reconstructString(String inputString,
                                    Map<Character, List<Integer>> potentialSequence) {
        if (potentialSequence.isEmpty()) return inputString;

        Map.Entry<Character, List<Integer>> entry = potentialSequence.entrySet().iterator().next();
        List<Integer> range = entry.getValue();

        if (range.size() != 2) {
            throw new IllegalArgumentException("Index range must contain exactly two elements.");
        }

        int start = range.get(0); // lower bound
        int end = range.get(1);   // upper bound

        if (start > end || start < 0 || end >= inputString.length()) {
            throw new IndexOutOfBoundsException("Invalid range: " + start + " to " + end);
        }

        String newString = inputString.substring(0, start) + inputString.substring(end + 1);
        return newString;
    }

    public void removeIntendedCharacters(Character character,
                                         Map<Character, List<Integer>> characterOccurrence,
                                         Map<Character, List<Integer>> intendedSequence) {
        List<Integer> indexes = characterOccurrence.get(character);
        indexes.removeIf(num -> num >= intendedSequence.get(character).getFirst()
                && num <= intendedSequence.get(character).getLast() );
    }

    public void indexesAfterLastRemovalMinusNumberOfRemovals(Map<Character, List<Integer>> characterOccurrence,
                                                             int numberOfRemovals,
                                                             int lastIndexOfRemoval) {
        for (Map.Entry<Character, List<Integer>> entry : characterOccurrence.entrySet()) {
            List<Integer> indexes = entry.getValue();
            for (int i = 0; i < indexes.size() - numberOfRemovals; i++) {
                int index = indexes.get(i);
                if(index > lastIndexOfRemoval) {
                    indexes.set(i, index - numberOfRemovals);
                }
            }
        }

    }

    public Boolean checkIfThereAreNextCharacter(String inputString, int currentIterationCharacterIndex) {
        int length = inputString.length(); // 5
        int currentIterationNextCharacterIndex = currentIterationCharacterIndex + 1;
        if(currentIterationNextCharacterIndex >= length) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkIfNextCharacterIsTheSame(String inputString, int currentIterationCharacterIndex) {
        int currentIterationNextCharacterIndex = currentIterationCharacterIndex + 1;
        if(inputString.charAt(currentIterationNextCharacterIndex) == inputString.charAt(currentIterationCharacterIndex)) {
            return true;
        } else {
            return false;
        }
    }


    public Map<Character, List<Integer>> checkIfMoreThanThreeCharacterSequence(Map<Character, List<Integer>> characterOccurrence) {
        final int MIN_LENGTH = 3;
        for (Map.Entry<Character, List<Integer>> entry : characterOccurrence.entrySet()) {
            List<Integer> characterOccurrenceList = entry.getValue();
            List<int[]> result = findConsecutiveSequences(characterOccurrenceList, MIN_LENGTH);
            if(result.isEmpty()) {
            } else {
                Map<Character, List<Integer>> foundSequence = new HashMap<>();
                foundSequence.put(entry.getKey(), Arrays.stream(result.get(0))
                        .boxed()
                        .collect(Collectors.toList()));
                return foundSequence;
            }
        }
        return new HashMap<>();
    }

    public List<int[]> findConsecutiveSequences(List<Integer> list, int minLength) {
        List<int[]> result = new ArrayList<>();
        if (list == null || list.size() < minLength) return result;

        TreeSet<Integer> sortedSet = new TreeSet<>(list);
        List<Integer> sortedList = new ArrayList<>(sortedSet);

        int start = sortedList.get(0);
        int prev = start;
        int length = 1;

        for (int i = 1; i < sortedList.size(); i++) {
            int current = sortedList.get(i);
            if (current == prev + 1) {
                length++;
            } else {
                if (length >= minLength) {
                    result.add(new int[]{prev - length + 1, prev});
                }
                length = 1;
            }
            prev = current;
        }

        if (length >= minLength) {
            result.add(new int[]{prev - length + 1, prev});
        }

        return result;
    }
}