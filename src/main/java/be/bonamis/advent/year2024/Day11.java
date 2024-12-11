package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day11 extends TextDaySolver {

  public Day11(InputStream inputStream) {
    super(inputStream);
  }

  public Day11(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    String input = this.puzzle.get(0);
    List<Long> numbers = Stream.of(input.split("\\s+")).map(Long::parseLong).toList();
    log.debug("numbers: {}", numbers);
    return blink(numbers, 25).size();
  }

  static List<Long> blink(List<Long> input, int times) {
    List<Long> result = new ArrayList<>(input);
    for (int i = 0; i < times; i++) {
      result = blink(result);
      log.debug("result: {}", result);
    }
    return result;
  }

  static List<Long> blink(List<Long> input) {
    log.debug("blink: {}", input);
    return input.stream().flatMap(Day11::transform).toList();
  }

  static Stream<Long> transform(Long input) {
    log.debug("transform: {}", input);
    if (input == 0) {
      return Stream.of(1L);
    }
    String inputString = String.valueOf(input);
    int length = inputString.length();
    if (length % 2 == 0) {
      int middle = length / 2;
      String left = inputString.substring(0, middle);
      String right = inputString.substring(middle);
      return Stream.of(Long.parseLong(left), Long.parseLong(right));
    }
    return Stream.of(input * 2024L);
  }

  @Override
  public long solvePart02() {
    String input = this.puzzle.get(0);
    List<Long> numbers = Stream.of(input.split("\\s+")).map(Long::parseLong).toList();
    return blink(numbers, 75).size();
  }
}
