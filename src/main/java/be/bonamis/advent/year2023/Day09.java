package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.math.*;
import java.util.*;
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
    return this.puzzle.size() + 1;
  }

  long solveLine(String line) {
    List<Long> originalList = Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList();
    List<List<Long>> list = new ArrayList<>();
    list.add(originalList);

    log.debug("{}", originalList);
    List<Long> list1 = differences(originalList);
    Long sum = getSum(list1);
    while (sum != 0) {
      list.add(list1);
      list1 = differences(list1);
      sum = getSum(list1);
    }
    list.add(list1);
    log.debug("{}", list);

    return list.stream().mapToLong(this::last).sum();
  }

  long last(List<Long> list) {
    return list.get(list.size() - 1);
  }

  private Long getSum(List<Long> list1) {
    return list1.stream().reduce(Long::sum).orElseThrow();
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
