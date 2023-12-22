package be.bonamis.advent.year2023;

import static be.bonamis.advent.year2023.Day19.WorkFlow.Rule.ACCEPTED;
import static be.bonamis.advent.year2023.Day19.WorkFlow.Rule.REJECTED;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day22 extends TextDaySolver {

  protected Day22(String input) {
    super(input);
  }

  @Override
  public long solvePart01() {
    return 9999;
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/22/2023_22_input.txt");
    Day22 day = new Day22(content);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
