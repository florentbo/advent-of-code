package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day12 extends DaySolver<String> {

  public Day12(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.parallelStream().map(this::solveRow2).reduce(0L, Long::sum);
  }

  List<Integer> damageCount(String input) {
    int count = 0;
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) == '#') {
        count++;
      } else {
        count = addCount(count, result);
      }
    }
    addCount(count, result);
    return result;
  }

  int addCount(int count, List<Integer> result) {
    if (count != 0) {
      result.add(count);
      count = 0;
    }
    return count;
  }

  long solveRow2(String row) {
    String[] split = row.split("\\s+");
    String conditionsInput = split[0];
    String damagesInput = split[1];
    log.debug("damagesInput: {}", damagesInput);
    List<Integer> damages = Arrays.stream(damagesInput.split(",")).map(Integer::parseInt).toList();
    log.debug("damages: {}", damages);

    log.debug("conditionsInput: {}", conditionsInput);
    int[] count = {0};
    generateHelper(0, conditionsInput, "", damages, count);
    return count[0];
  }

  private void generateHelper(
      int index,
      String conditionsInput,
      String currentCombination,
      List<Integer> damages,
      int[] count) {
    List<Integer> damagedCount = damageCount(currentCombination);
    int foundDamagesSize = damagedCount.size();

    IntPredicate sizePredicate = i -> damagedCount.size() > damages.size();
    IntPredicate intPredicate3 =
        i ->
            (i > 0 && foundDamagesSize > 1)
                && (!Objects.equals(damagedCount.get(i - 1), damages.get(i - 1)));
    IntPredicate intPredicate = i -> damagedCount.get(i) > damages.get(i);

    boolean anyMatch =
        IntStream.range(0, foundDamagesSize)
            .anyMatch(sizePredicate.or(intPredicate3.or(intPredicate)));

    if (anyMatch) {
      return;
    }

    if (index == conditionsInput.length()) {
      if (damagedCount.equals(damages)) {
        count[0]++;
      }
      return;
    }

    char currentChar = conditionsInput.charAt(index);

    if (currentChar == '?') {
      generateHelper(index + 1, conditionsInput, currentCombination + '.', damages, count);
      generateHelper(index + 1, conditionsInput, currentCombination + '#', damages, count);
    } else {
      generateHelper(index + 1, conditionsInput, currentCombination + currentChar, damages, count);
    }
  }

  String unfold(String row) {
    String[] split = row.split("\\s+");
    String conditionsInput = split[0];
    String damagesInput = split[1];

    return repeat(conditionsInput, "?") + " " + repeat(damagesInput, ",");
  }

  private String repeat(String input, String delimiter) {
    return IntStream.range(0, 5).mapToObj(i -> input).collect(Collectors.joining(delimiter));
  }

  @Override
  public long solvePart02() {

    return this.puzzle.parallelStream()
        .map(this::clean)
        .map(this::solvePart02Row)
        .reduce(0L, Long::sum);
  }

  private String clean(String input) {
    return input.replaceAll("\\.{2,}", ".");
  }

  long solvePart02Row(String row) {
    return solveRow2(unfold(row));
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/12/2023_12_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day12 day = new Day12(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    // log.info("solution part 2: {}", day.solvePart02());
  }
}
