package be.bonamis.advent.year2018;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Day01 extends DaySolver<String> {

  public Day01(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return puzzle.stream()
        .map(
            s -> {
              int sign = s.charAt(0) == '+' ? 1 : -1;
              return Integer.parseInt(s.substring(1)) * sign;
            })
        .reduce(0, Integer::sum);
  }

  @Override
  public long solvePart02() {
    int sum = 0;
    Set<Integer> frequencies = new HashSet<>();
    boolean found = false;
    int index = 0;

    while (!found) {
      int sign = puzzle.get(index).charAt(0) == '+' ? 1 : -1;
      sum += Integer.parseInt(puzzle.get(index).substring(1)) * sign;
      if (frequencies.contains(sum)) {
        found = true;
      }
      frequencies.add(sum);
      index = (index + 1) % puzzle.size();
    }

    return sum;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2018/01/2018_01_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day01 day = new Day01(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
