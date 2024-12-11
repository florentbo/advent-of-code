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
    return solve(25);
  }

  private long solve(int times) {
    String input = this.puzzle.get(0);
    List<Long> initial = Stream.of(input.split("\\s+")).map(Long::parseLong).toList();
    log.debug("numbers: {}", initial);
    return blinkWithCounter(initial, times);
  }

  static long blinkWithCounter(List<Long> input, int times) {
    Map<Long, Long> counter = new HashMap<>();

    for (Long num : input) {
      counter.merge(num, 1L, Long::sum);
    }

    for (int i = 0; i < times; i++) {
      counter = blinkCounter(counter);
      log.debug(
          "Step {}, total numbers: {}",
          i,
          counter.values().stream().mapToLong(Long::longValue).sum());
    }

    return counter.values().stream().mapToLong(Long::longValue).sum();
  }

  static Map<Long, Long> blinkCounter(Map<Long, Long> counter) {
    Map<Long, Long> next = new HashMap<>();

    for (var entry : counter.entrySet()) {
      Long num = entry.getKey();
      Long count = entry.getValue();

      if (num == 0) {
        next.merge(1L, count, Long::sum);
        continue;
      }

      String numStr = String.valueOf(num);
      int length = numStr.length();

      if (length % 2 == 0) {
        int middle = length / 2;
        long left = Long.parseLong(numStr.substring(0, middle));
        long right = Long.parseLong(numStr.substring(middle));
        next.merge(left, count, Long::sum);
        next.merge(right, count, Long::sum);
      } else {
        next.merge(num * 2024L, count, Long::sum);
      }
    }

    return next;
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
    return solve(75);
  }
}
