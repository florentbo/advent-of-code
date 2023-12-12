package be.bonamis.advent.year2023;

public class Combinations {

  public static void generateCombinations(String inputStr) {
    generateHelper(0, inputStr, "");
  }

  private static void generateHelper(int index, String inputStr, String currentCombination) {
    if (index == inputStr.length()) {
      System.out.println(currentCombination);
      return;
    }

    char currentChar = inputStr.charAt(index);

    if (currentChar == '?') {
      generateHelper(index + 1, inputStr, currentCombination + '.');
      generateHelper(index + 1, inputStr, currentCombination + '#');
    } else {
      generateHelper(index + 1, inputStr, currentCombination + currentChar);
    }
  }

  public static void main(String[] args) {
    String inputStr = "???.###";
    generateCombinations(inputStr);
  }
}
