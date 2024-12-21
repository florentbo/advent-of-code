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

  private final Map<String, Long> memo = new HashMap<>();

  public boolean canBeMade(String design, List<String> patterns) {
    if (memo.containsKey(design)) {
      return memo.get(design) > 0;
    }
    log.debug("design: {}", design);
    for (String pattern : patterns) {
      log.debug("pattern: {}", pattern);
      if (design.startsWith(pattern)) {
        String newDesign = design.substring(pattern.length());
        log.debug("newDesign: {}", newDesign);
        if (!newDesign.isEmpty()) {
          if (canBeMade(newDesign, patterns)) {
            boolean b = memo.containsKey(design);
            if (b) {
              memo.put(design, memo.get(design) + 1);
            } else {
              memo.put(design, 1L);
            }
            return true;
          } else {
            memo.put(design, 0L);
          }
        } else {
          boolean b = memo.containsKey(design);
          if (b) {
            memo.put(design, memo.get(design) + 1);
          } else {
            memo.put(design, 1L);
          }
          log.debug("found pattern: {}", pattern);
          log.debug("memo: {}", memo);
          return true;
        }
      }
    }
    return false;
  }

  public long canBeMadePart0222(String design, List<String> patterns) {
    canBeMade22(design, patterns);
    // log.debug("canBeMade: {}", canBeMade);
    return memo.getOrDefault(design, 0L);
  }

  public long canBeMadePart0233(String original, List<String> patterns) {
    long count = 0;
    canBeMadePart02(original, original, patterns, count);
    log.debug("memo2: {}", memo2);
    return memo2.getOrDefault(original, 0L);
  }

  private final Map<String, Long> memo2 = new HashMap<>();

  // number of found is ok here
  public long canBeMadePart02(String original, String design, List<String> patterns, long count) {

    /*if (memo.containsKey(design)) {
      log.debug("found");
      count = 1;
    }*/
    for (String pattern : patterns) {
      if (design.startsWith(pattern)) {
        String newDesign = design.substring(pattern.length());
        if (!newDesign.isEmpty()) {
          if (canBeMadePart02(original, newDesign, patterns, count) > 0) {
            // memo.put(design, true);
            // count++;
          } else {
            // memo.put(design, false);
          }
        } else {
          // memo.put(design, true);
          count++;
          log.debug("found and count: {}", count);
          Long orDefault = memo2.getOrDefault(original, 0L);
          memo2.put(original, orDefault + 1);
        }
      }
    }
    return count;
  }

  public void canBeMade22(String design, List<String> patterns) {
    /*if (memo.containsKey(design)) {
      return memo.get(design) > 0;
    }*/
    // log.debug("design: {}", design);
    for (String pattern : patterns) {
      // log.debug("pattern: {}", pattern);
      if (design.startsWith(pattern)) {
        String newDesign = design.substring(pattern.length());
        // log.debug("newDesign: {}", newDesign);
        if (!newDesign.isEmpty()) {
          if (canBeMade(newDesign, patterns)) {
            boolean b = memo.containsKey(design);
            if (b) {
              memo.put(design, memo.get(design) + 1);
            } else {
              memo.put(design, 1L);
            }
            //     return true;
          } else {
            memo.put(design, 0L);
          }
        } else {
          boolean b = memo.containsKey(design);
          if (b) {
            memo.put(design, memo.get(design) + 1);
          } else {
            memo.put(design, 1L);
          }
          log.debug("found pattern: {}", pattern);
          log.debug("memo: {}", memo);
          // return true;
        }
      }
    }
    // return false;
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
