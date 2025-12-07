package be.bonamis.advent.year2025;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day05 extends TextDaySolver {

  private final Input input;

  public Day05(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day05(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  record Input(FreshIngredientsRanges ranges, AvailableIngredients availableIngredient) {
    record FreshIngredientsRanges(List<FreshIngredientsRange> ranges) {}

    record FreshIngredientsRange(long start, long end) {
      static FreshIngredientsRange of(String line) {
        String[] split = line.split("-");
        return new FreshIngredientsRange(Long.parseLong(split[0]), Long.parseLong(split[1]));
      }

      boolean isFresh(long ingredient) {
        return ingredient >= start && ingredient <= end;
      }
    }

    record AvailableIngredients(List<Long> ingredients) {
      static AvailableIngredients of(List<String> ingredients) {
        List<Long> ingredientValues = ingredients.stream().map(Long::parseLong).toList();
        return new AvailableIngredients(ingredientValues);
      }
    }

    boolean isFresh(Long ingredient) {
      return this.ranges.ranges().stream().anyMatch(r -> r.isFresh(ingredient));
    }

    static Input of(List<String> lines) {
      int emptyLineIndex = lines.indexOf("");
      List<FreshIngredientsRange> ranges =
          lines.subList(0, emptyLineIndex).stream().map(FreshIngredientsRange::of).toList();
      AvailableIngredients availableIngredients =
          AvailableIngredients.of(lines.subList(emptyLineIndex + 1, lines.size()));

      return new Input(new FreshIngredientsRanges(ranges), availableIngredients);
    }
  }

  @Override
  public long solvePart01() {
    return this.input.availableIngredient().ingredients().stream()
        .filter(this.input::isFresh)
        .count();
  }

  @Override
  public long solvePart02() {
    return 1000;
  }
}
