package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.EAST;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
@Getter
public class Day15 extends DaySolver<String> {

  public Day15(String puzzle) {
    super(Collections.singletonList(puzzle));
  }

  @Override
  public long solvePart01() {
    List<String> list = Arrays.asList(this.puzzle.get(0).split(","));
    log.info("list size: {}", list.size());
    return list.stream().map(String::strip).map(this::running).reduce(Long::sum).orElseThrow();
  }

  long running(String input) {
    long result = 0;
    char[] charArray = input.toCharArray();
    for (char c : charArray) {
      result = modify(result, c);
    }
    return result;
  }

  long modify(long start, char hChar) {
    return ((start + (int) hChar) * 17) % 256;
  }

  @Override
  public long solvePart02() {
    return 99;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/15/2023_15_input.txt");
    Day15 day = new Day15(content);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
