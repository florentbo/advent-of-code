package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.function.Predicate;

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

    return this.puzzle.stream().map(this::solveRow).reduce(0L, Long::sum);
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

  long solveRow(String row) {
    String[] split = row.split("\\s+");
    String conditionsInput = split[0];
    String damagesInput = split[1];
    log.debug("conditionsInput: {}", conditionsInput);
    List<String> conditions = generateCombinations(conditionsInput);

    log.debug("damagesInput: {}", damagesInput);
    List<Integer> damages = Arrays.stream(damagesInput.split(",")).map(Integer::parseInt).toList();
    log.debug("damages: {}", damages);

    return conditions.stream()
        .map(this::damageCount)
        .filter(count -> count.equals(damages))
        .count();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/12/2023_12_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day12 day = new Day12(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
