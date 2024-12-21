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
        if (!newDesign.isEmpty()) {
          if (canBeMade(newDesign, patterns)) {
            memo.put(design, true);
            return true;
          } else {
            memo.put(design, false);
          }
        } else {
          memo.put(design, true);
          return true;
        }
      }
    }
    return false;
  }

  public long canBeMade2(String design, List<String> patterns) {
    long count = 0;
    if (memo.containsKey(design)) {
      count = 1;
    }
    log.debug("design: {}", design);
    for (String pattern : patterns) {
      log.debug("pattern: {}", pattern);
      if (design.startsWith(pattern)) {
        String newDesign = design.substring(pattern.length());
        log.debug("newDesign: {}", newDesign);
        if (!newDesign.isEmpty()) {
          if (canBeMade2(newDesign, patterns) > 0) {
            memo.put(design, true);
            count++;
          } else {
            memo.put(design, false);
          }
        } else {
          memo.put(design, true);
          count++;
        }
      }
    }
    return count;
  }

  public long canBeMadePart02(String design, List<String> patterns) {
    long count = 0;
    if (memo.containsKey(design)) {
      count = 1;
    }
    for (String pattern : patterns) {
      if (design.startsWith(pattern)) {
        String newDesign = design.substring(pattern.length());
        if (!newDesign.isEmpty()) {
          if (canBeMadePart02(newDesign, patterns) > 0) {
            memo.put(design, true);
            count++;
            // return count;
          } else {
            memo.put(design, false);
          }
        } else {
          memo.put(design, true);
          count++;
          // return count;
        }
      }
    }
    return count;
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
    int size = this.input.designs().size();
    return IntStream.range(0, size)
        .filter(
            i -> {
              log.info("design index {}: {} of  {}", i, this.input.designs().get(i), size);
              return canBeMade(this.input.designs().get(i), this.input.patterns());
            })
        .count();
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
