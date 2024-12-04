package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day04 extends TextDaySolver {

  public Day04(InputStream inputStream) {
    super(inputStream);
  }

  public Day04(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    CharGrid grid = new CharGrid(this.puzzle);

    return Stream.of(grid.columnsAsLines(), grid.rowsAsLines(), grid.diagonalsAsLines())
        .flatMap(List::stream)
        .mapToInt(Day04::countWord)
        .sum();
  }

  public static int countWord(String text) {
    return countWordOccurrences(text, "XMAS") + countWordOccurrences(text, "SAMX");
  }

  public static int countWordOccurrences(String text, String word) {
    return (int)
        IntStream.range(0, text.length() - word.length() + 1)
            .filter(i -> text.substring(i).startsWith(word))
            .count();
  }

  public static int countWord2(String text) {
    return countWordOccurrences(text, "MAS") + countWordOccurrences(text, "SAM");
  }

  @Override
  public long solvePart02() {
    CharGrid grid = new CharGrid(this.puzzle);

    return grid.subGrids(3).stream()
        .filter(
            subgrid -> subgrid.diagonalsAsLines().stream().mapToInt(Day04::countWord2).sum() == 2)
        .count();
  }
}
