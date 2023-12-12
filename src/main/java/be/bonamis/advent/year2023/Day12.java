package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.year2023.StringCombinations.generateCombinations;

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

  /*long solveRow(String row) {
    String[] split = row.split("\\s+");
    String conditionsInput = split[0];
    String damagesInput = split[1];
    log.debug("conditionsInput: {}", conditionsInput);
    List<String> conditions = generateCombinations(conditionsInput);
    log.debug("damagesInput: {}", damagesInput);
    List<Integer> damages = Arrays.stream(damagesInput.split(",")).map(Integer::parseInt).toList();
    log.debug("damages: {}", damages);

    return conditions.parallelStream()
        .map(this::damageCount)
        .filter(count -> count.equals(damages))
        .count();
  }*/

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
    int damageToFoundSize = damages.size();

    IntPredicate sizePredicate = i -> damagedCount.size() > damages.size();
    IntPredicate intPredicate = i -> damagedCount.get(i) > damages.get(i);
    IntPredicate intPredicate2 = i -> foundDamagesSize > damageToFoundSize;
    IntPredicate intPredicate3 =
        i -> {
          int index1 = i - 1;
          // log.debug("stream index {} calculated index {} damagedCount: {} damages: {}", i,
          // index1, damagedCount, damages);
          /*if (test) {
            log.debug("+++++++ test is true");
          }*/
          return (i > 0 && foundDamagesSize > 1)
              && (!Objects.equals(damagedCount.get(index1), damages.get(index1)));
        };

    boolean anyMatch =
        IntStream.range(0, foundDamagesSize)
            .anyMatch(sizePredicate.or(intPredicate3.or(intPredicate).or(intPredicate2)));
    if (anyMatch) {
      return;
    }
    log.debug(
        "currentCombination: {}, damagedCount: {} damages: {} anyMatch: {}",
        currentCombination,
        damagedCount,
        damages,
        anyMatch);

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
        .map(this::unfold)
        .map(this::solveRow2)
        .reduce(0L, Long::sum);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/12/2023_12_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day12 day = new Day12(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
