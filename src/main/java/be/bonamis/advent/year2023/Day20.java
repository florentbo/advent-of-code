package be.bonamis.advent.year2023;


import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day20 extends DaySolver<String> {

  public Day20(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return 101L;
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/19/2023_19_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day20 day = new Day20(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
