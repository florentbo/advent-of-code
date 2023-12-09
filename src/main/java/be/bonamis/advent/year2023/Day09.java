package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day09 extends DaySolver<String> {

  public Day09(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.stream().map(this::solveLine).reduce(0L, Long::sum);
  }

  @Override
  public long solvePart02() {
    return this.puzzle.stream().map(this::solveLine2).reduce(0L, Long::sum);
  }

  long solveLine2(String line) {
    List<Long> firstElements = differences(line).stream().mapToLong(this::first).boxed().toList();

    Long reduce =
        IntStream.range(0, firstElements.size())
            .mapToObj(i -> firstElements.get(firstElements.size() - 1 - i))
            .reduce(0L, (a, b) -> b - a);
    log.debug("reduce {}", reduce);

    return reduce;
  }

  long solveLine(String line) {
    List<List<Long>> list = differences(line);
    list.forEach(l -> log.debug("{}", l));

    return list.stream().mapToLong(this::last).sum();
  }

  private List<List<Long>> differences(String line) {
    List<Long> originalList = Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList();
    List<List<Long>> list = new ArrayList<>();
    list.add(originalList);

    log.debug("input {}", originalList);
    List<Long> list1 = differences(originalList);
    boolean notContainsOnlyZero = notContainsOnlyZero(list1);
    while (notContainsOnlyZero) {
      list.add(list1);
      list1 = differences(list1);
      log.debug("input {}", list1);
      notContainsOnlyZero = notContainsOnlyZero(list1);
      log.debug("containsOnlyZero {}", notContainsOnlyZero);
    }
    list.add(list1);
    return list;
  }

  private boolean notContainsOnlyZero(List<Long> list) {
    return !allZero(list);
  }

  long last(List<Long> list) {
    return list.get(list.size() - 1);
  }

  long first(List<Long> list) {
    return list.get(0);
  }

  private boolean allZero(List<Long> list) {
    return list.stream().allMatch(Predicate.isEqual(0L));
  }

  private List<Long> differences(List<Long> list) {
    return IntStream.range(1, list.size())
        .mapToLong(i -> list.get(i) - list.get(i - 1))
        .boxed()
        .toList();
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/09/2023_09_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day09 day = new Day09(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  public record Node(String arrival, String left, String right) {}
}
