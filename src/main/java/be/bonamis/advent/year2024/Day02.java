package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day02 extends TextDaySolver {

  public Day02(InputStream inputStream) {
    super(inputStream);
  }

  public Day02(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.stream().filter(Day02::isSafe).count();
  }

  static boolean isSafe(String line) {
    List<Integer> numbersList = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
    return listIsSafe(numbersList);
  }

  @Override
  public long solvePart02() {
    return this.puzzle.stream().filter(Day02::isSafeWithTolerance).count();
  }

  static boolean isSafeWithTolerance(String line) {
    String[] numbers = line.split(" ");
    List<Integer> numbersList =
            new java.util.ArrayList<>(Arrays.stream(numbers).map(Integer::parseInt).toList());

    return isSafe(line) || isSafeRemovingOneLevel(numbersList);
  }

  private static boolean listIsSafe(List<Integer> numbersList) {
    boolean isDecreasing =
        IntStream.range(0, numbersList.size() - 1)
            .allMatch(
                i -> {
                  int diff = numbersList.get(i) - numbersList.get(i + 1);
                  return diff >= 1 && diff <= 3;
                });

    boolean isIncreasing =
        IntStream.range(0, numbersList.size() - 1)
            .allMatch(
                i -> {
                  int diff = numbersList.get(i + 1) - numbersList.get(i);
                  return diff >= 1 && diff <= 3;
                });

    return isDecreasing || isIncreasing;
  }

  private static boolean isSafeRemovingOneLevel(List<Integer> numbersList) {
    return IntStream.range(0, numbersList.size())
        .mapToObj(i -> partialListIsSafe(numbersList, i))
        .anyMatch(Boolean::booleanValue);
  }

  private static boolean partialListIsSafe(List<Integer> numbersList, int index) {
    List<Integer> initialList = new java.util.ArrayList<>(numbersList);
    initialList.remove(index);
    return listIsSafe(initialList);
  }
}
