package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day06 extends DaySolver<String> {

  public Day06(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.size();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/04/2023_04_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day06 day = new Day06(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
