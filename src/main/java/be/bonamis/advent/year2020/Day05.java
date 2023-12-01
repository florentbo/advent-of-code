package be.bonamis.advent.year2020;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.util.Pair;

@Slf4j
public class Day05 extends DaySolver<String> {

  public Day05(List<String> puzzle) {
    super(puzzle);
  }

  public static long seatId(String line) {
    String[] letters = line.split("");
    Pair<Integer, Integer> moved = Pair.of(0, 127);
    int latestLetterIndex = letters.length - 3;
    for (int i = 0; i < latestLetterIndex; i++) {
      log.debug("char: i {} val {}", i, letters[i]);
      moved = move(letters[i], moved);
      log.debug("new moved {}", moved);
    }

    int rowNumber = moved.getFirst();
    log.debug("rowNumber {}", rowNumber);

    Pair<Integer, Integer> column = Pair.of(0, 7);
    for (int i = latestLetterIndex; i < letters.length; i++) {
      log.debug("char: i {} val {}", i, letters[i]);
      column = move(letters[i], column);
      log.debug("new moved {}", column);
    }
    log.debug("column {}", column);
    int colNumber = column.getFirst();
    return rowNumber * 8L + colNumber;
  }

  public static Pair<Integer, Integer> move(String direction, Pair<Integer, Integer> rows) {
    int i = rows.getSecond() - rows.getFirst();
    if ("F".equals(direction) || "L".equals(direction)) {
      return Pair.of(rows.getFirst(), i / 2 + rows.getFirst());
    } else {
      return Pair.of(roundUp(i, 2) + rows.getFirst(), rows.getSecond());
    }
  }

  public static int roundUp(int num, int divisor) {
    return (num + divisor - 1) / divisor;
  }

  @Override
  public long solvePart01() {
    return this.puzzle.stream().map(Day05::seatId).max(Long::compare).orElseThrow();
  }

  @Override
  public long solvePart02() {
    List<Long> list = this.puzzle.stream().map(Day05::seatId).sorted().toList();

    List<Long> gaps = new ArrayList<>();

    for (int i = 0; i < list.size() - 1; i++) {
      long current = list.get(i);
      long next = list.get(i + 1);
      if (next - current > 1) {
        gaps.add(list.get(i + 1) - 1);
      }
    }

    return gaps.get(0);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2020/05/2020_05_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day05 day = new Day05(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
