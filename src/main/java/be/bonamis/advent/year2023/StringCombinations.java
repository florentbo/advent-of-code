package be.bonamis.advent.year2023;

import java.util.ArrayList;
import java.util.List;

public class StringCombinations {

    public static List<String> generateCombinations(String inputStr) {
        return generateHelper(0, inputStr, "");
    }

    private static List<String> generateHelper(int index, String inputStr, String currentCombination) {
        if (index == inputStr.length()) {
            return List.of(currentCombination);
        }

        char currentChar = inputStr.charAt(index);

        if (currentChar == '?') {
            List<String> combinationsWithDot = generateHelper(index + 1, inputStr, currentCombination + '.');
            List<String> combinationsWithHash = generateHelper(index + 1, inputStr, currentCombination + '#');
            return combineLists(combinationsWithDot, combinationsWithHash);
        } else {
            return generateHelper(index + 1, inputStr, currentCombination + currentChar);
        }
    }

    private static List<String> combineLists(List<String> list1, List<String> list2) {
        List<String> combinedList = new ArrayList<>(list1);
        combinedList.addAll(list2);
        return combinedList;
    }

    public static void main(String[] args) {
        String inputStr = "???.###";
        List<String> result = generateCombinations(inputStr);

        result.forEach(System.out::println);
    }
}
