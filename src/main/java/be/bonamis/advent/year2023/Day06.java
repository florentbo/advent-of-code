package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.ArrayList;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day06 extends DaySolver<String> {

  private final List<Long> times;
  private final List<Long> distances;

  public Day06(List<String> puzzle) {
    super(puzzle);
    this.times = parseTimes(this.puzzle.get(0));
    this.distances = parseDistances(this.puzzle.get(1));
  }

  private List<Long> parseDistances(String input) {
    List<Long> times =
        Arrays.stream(input.split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
    log.debug("parseDistances: {}", times);
    return times;
  }

  private List<Long> parseTimes(String input) {
    List<Long> times =
        Arrays.stream(input.split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
    log.debug("times: {}", times);
    return times;
  }

  @Override
  public long solvePart01() {
    long total = 1;
    for (int i = 0; i < this.times.size(); i++) {
      long count = solve(this.times.get(i), this.distances.get(i));
      log.debug("count: {}", count);
      total *= count;
    }

    return total;
  }

  private long solve(Long time, Long distance) {
    long remaining = time;
    long count = 0;
    for (long speed = 0; speed < time; speed++) {
      long remainingTime2 = remaining - speed;
      long total = speed * remainingTime2;
      if (total > distance) {
        count++;
      }
    }
    return count;
  }

  @Override
  public long solvePart02() {
    Long result = Long.valueOf(String.join("", this.times.stream().map(String::valueOf).toList()));
    Long result2 =
        Long.valueOf(String.join("", this.distances.stream().map(String::valueOf).toList()));
    return solve(result, result2);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/06/2023_06_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day06 day = new Day06(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
