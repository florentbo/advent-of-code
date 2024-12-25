package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import be.bonamis.advent.common.CharGrid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day25 extends TextDaySolver {
  private final Inputs inputs;

  public Day25(InputStream inputStream) {
    super(inputStream);
    this.inputs = Inputs.of(this.puzzle);
  }

  public Day25(String input) {
    super(input);
    this.inputs = Inputs.of(this.puzzle);
  }

  record Inputs(List<KeyLock> inputs) {

    public static Inputs of(List<String> puzzle) {
      log.debug("puzzle size: {}", puzzle.size());
      List<KeyLock> keyLocks =
          IntStream.range(0, (puzzle.size() + 1) / 8)
              .mapToObj(
                  i -> {
                    List<String> strings = puzzle.subList(i * 8, i * 8 + 7);
                    if (i == 0) {
                      strings.forEach(s -> log.debug("row: {}", s));
                    }
                    return KeyLock.of(strings);
                  })
              .toList();

      return new Inputs(keyLocks);
    }

    List<KeyLock> keys() {
      return this.inputs.stream().filter(KeyLock::isKey).toList();
    }

    List<KeyLock> locks() {
      return this.inputs.stream().filter(KeyLock::isLock).toList();
    }

    record KeyLock(CharGrid grid) {
      private static final char FILLED = '#';
      private static final char EMPTY = '.';

      public KeyLock(List<String> puzzle) {
        this(new CharGrid(puzzle));
      }

      public static KeyLock of(List<String> strings) {
        return new KeyLock(strings);
      }

      boolean isLock() {
        String firstRow = firstRow();

        return firstRow.chars().allMatch(c -> c == FILLED);
      }

      boolean isKey() {
        return !isLock();
      }

      private String firstRow() {
        List<Point> points = grid.row2(0);
        return grid.toLine2(points);
      }

      List<Integer> pinHeights() {
        log.debug("grid:  height: {} and width: {}", grid.getHeight(), grid.getWidth());
        return IntStream.range(0, grid.getHeight())
            .mapToObj(
                i -> {
                  String column = grid.toLine2(grid.columns2(i));
                  log.debug("column: {}", column);
                  int count = (int) column.chars().filter(c -> c == FILLED).count();
                  return count - 1;
                })
            .toList();
      }

      public boolean fit(KeyLock other) {
        List<Integer> otherPinHeights = other.pinHeights();
        return IntStream.range(0, this.pinHeights().size())
            .allMatch(i -> this.pinHeights().get(i) + otherPinHeights.get(i) <= 5);
      }
    }
  }

  @Override
  public long solvePart01() {
    List<Inputs.KeyLock> keys = inputs.keys();
    List<Inputs.KeyLock> locks = inputs.locks();

    // all possible combinations of keys and locks

   // keys.stream().flatMap(key -> locks.stream().map(lock -> key.fit(lock)));
    return keys.stream()
        .flatMap(key -> locks.stream().map(lock -> key.fit(lock) ? 1 : 0))
        .reduce(0, Integer::sum);
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
