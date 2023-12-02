package be.bonamis.advent.year2015;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class Day02 extends DaySolver<String> {
  protected Day02(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.stream().map(this::paper).reduce(Long::sum).orElseThrow();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.stream().map(this::ribbons).reduce(Long::sum).orElseThrow();
  }

  public long paper(String input) {
    String[] dimensions = input.split("x");
    int l = Integer.parseInt(dimensions[0]);
    int w = Integer.parseInt(dimensions[1]);
    int h = Integer.parseInt(dimensions[2]);
    return paper(l, w, h);
  }

  private long ribbons(String input) {
    String[] dimensions = input.split("x");
    int l = Integer.parseInt(dimensions[0]);
    int w = Integer.parseInt(dimensions[1]);
    int h = Integer.parseInt(dimensions[2]);
    return ribbon(l, w, h);
  }

  public long paper(int l, int w, int h) {
    return boxArea(l, w, h) + min(l * w, w * h, h * l);
  }

  public long min(int a, int b, int c) {
    return Math.min(Math.min(a, b), c);
  }

  public long boxArea(int l, int w, int h) {
    return 2L * l * w + 2L * w * h + 2L * h * l;
  }

  public long ribbon(int l, int w, int h) {
    List<Integer> sides = Stream.of(l, w, h).sorted().toList();
    return 2L * (sides.get(0) + sides.get(1)) + (long) l * w * h;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2015/02/2015_02_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day02 day = new Day02(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
