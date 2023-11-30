package be.bonamis.advent.year2020;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day05 extends DaySolver<String> {

  public Day05(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return puzzle.size();
  }

  @Override
  public long solvePart02() {

    return puzzle.size() + 1L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/01/2023_01_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day05 day = new Day05(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
