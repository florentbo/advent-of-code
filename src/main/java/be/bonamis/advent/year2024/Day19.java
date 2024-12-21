package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
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

  public boolean canBeMade(String design, List<String> patterns) {
    log.debug("design: {}", design);
    for (int i = 0; i < patterns.size(); i++) {
      String pattern = patterns.get(i);
      log.debug("pattern index {}: {}", i, pattern);
      log.debug("pattern: {}", pattern);
      if (design.startsWith(pattern)) {
        String newDesign = design.substring(pattern.length());
        log.debug("newDesign: {}", newDesign);
        if (!newDesign.isEmpty()) {
          if (canBeMade(newDesign, patterns)) return true;
        } else {
          return true;
        }
      }
    }
    return false;
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
        .filter(i -> {
          log.info("design index {}: {} of  {}", i, this.input.designs().get(i), size);
            return canBeMade(this.input.designs().get(i), this.input.patterns());
        })
        .count();
    //return this.input.designs().stream().filter(d -> canBeMade(d, this.input.patterns())).count();
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
