package be.bonamis.advent.year2015;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.*;

@Slf4j
public class Day01 extends DaySolver<String> {

  public Day01(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return solve(this.puzzle.get(0));
  }

  long solve(String input) {
    Map<String, Long> map =
        Arrays.stream(input.split(""))
            .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    return count(map);
  }

  long count(Map<String, Long> map) {
    Long down = map.get(")");
    Long up = map.get("(");
    return (up != null ? up : 0L) - (down != null ? down : 0L);
  }

  @Override
  public long solvePart02() {
    return IntStream.range(0, this.puzzle.get(0).length() + 1)
        .mapToObj(
            i -> Pair.of(solve(this.puzzle.get(0).substring(0, i)), i))
        .filter(pair -> pair.getLeft() < 0)
        .findFirst()
        .orElseThrow()
        .getRight();
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2015/01/2015_01_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day01 day = new Day01(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
