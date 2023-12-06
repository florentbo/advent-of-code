package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.ArrayList;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day06 extends DaySolver<String> {

  private final List<Integer> times;
  private final List<Integer> distances;

  public Day06(List<String> puzzle) {
    super(puzzle);
    this.times = parseTimes(this.puzzle.get(0));
    this.distances = parseDistances(this.puzzle.get(1));
  }

  private List<Integer> parseDistances(String input) {
    List<Integer> times =
        Arrays.stream(input.split(":")[1].trim().split("\\s+")).map(Integer::parseInt).toList();
    log.debug("parseDistances: {}", times);
    return times;
  }

  private List<Integer> parseTimes(String input) {
    List<Integer> times =
        Arrays.stream(input.split(":")[1].trim().split("\\s+")).map(Integer::parseInt).toList();
    log.debug("times: {}", times);
    return times;
  }

  @Override
  public long solvePart01() {
    Integer time = this.times.get(0);
    Integer distance = this.distances.get(0);

    for (int i = 0; i < this.times.size(); i++) {

    }


    int count = solve(time, distance);
    log.debug("count: {}", count);

    return 999L;
  }

  private int solve(Integer time, Integer distance) {
    int remaining = time;
    int count = 0;
    for (int speed = 0; speed < time; speed++) {
      int remainingTime2 = remaining - speed;
      int total = speed * remainingTime2;
      log.debug("total: {}", total);
      if (total >= distance) {
        count++;
      }
    }
    return count;
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/06/2023_06_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day06 day = new Day06(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
