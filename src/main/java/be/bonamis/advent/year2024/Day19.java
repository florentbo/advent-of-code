package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day19 extends TextDaySolver {

  private final Input input;

  public Day19(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day19(List<String> puzzle) {
    super(puzzle);
    this.input = Input.of(this.puzzle);
  }

  private final Map<String, Boolean> memo = new HashMap<>();
  private final Map<String, Long> memo2 = new HashMap<>();

  public boolean canBeMade(String design, List<String> patterns) {
    if (memo.containsKey(design)) {
      return memo.get(design);
    }
    log.debug("design: {}", design);
    for (String pattern : patterns) {

      log.debug("pattern: {}", pattern);
      if (design.startsWith(pattern)) {
        String newDesign = design.substring(pattern.length());
        log.debug("newDesign: {}", newDesign);
        if (newDesign.isEmpty()) {
          memo.put(design, true);
          return true;
        } else {
          if (canBeMade(newDesign, patterns)) {
            memo.put(design, true);
            return true;
          } else {
            log.debug("memo in set to false: {} design: {}", memo, design);
            memo.put(design, false);
          }
        }
      }
    }
    return false;
  }

  public long canBeMadePart02(final String design, List<String> patterns) {

    if (memo2.containsKey(design)) {
      return memo2.get(design);
    }

    if (design.isEmpty()) {
      return 1L;
    }

    long total =
        patterns.stream()
            .filter(design::startsWith)
            .mapToLong(p -> canBeMadePart02(design.substring(p.length()), patterns))
            .sum();

    memo2.put(design, total);
    return total;
  }

  record Input(List<String> patterns, List<String> designs) {
    public static Input of(List<String> puzzle) {
      Integer blank =
          IntStream.range(0, puzzle.size())
              .filter(i -> puzzle.get(i).isEmpty())
              .boxed()
              .findFirst()
              .orElseThrow();
      log.debug("blank: {}", blank);

      List<String> firstPart = puzzle.subList(0, blank);
      log.debug("firstPart: {}", firstPart);
      List<String> patterns = Arrays.stream(firstPart.get(0).split(", ")).toList();
      log.debug("patterns: {}", patterns);

      List<String> secondPart = puzzle.subList(blank + 1, puzzle.size());
      log.debug("secondPart: {}", secondPart);

      return new Input(patterns, secondPart);
    }
  }

  @Override
  public long solvePart01() {
    return this.input.designs().stream()
        .filter(d -> canBeMadePart02(d, this.input.patterns()) > 0)
        .count();
  }

  @Override
  public long solvePart02() {
    return this.input.designs().stream()
        .map(d -> canBeMadePart02(d, this.input.patterns()))
        .reduce(0L, Long::sum);
  }
}
