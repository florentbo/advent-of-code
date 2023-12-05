package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day05 extends DaySolver<String> {

  private static final String MAP = " map:";
  private final Seeds seeds;
  private final List<List<LineOfMap>> lineMaps;

  public Day05(List<String> puzzle) {
    super(puzzle);
    this.seeds = parseSeeds(this.puzzle.get(0));
    this.lineMaps = lineMaps(puzzle);
  }

  private List<List<LineOfMap>> lineMaps(List<String> puzzle) {
    long count =
        IntStream.range(0, this.puzzle.size())
            .filter(x -> this.puzzle.get(x).contains(MAP))
            .count();
    log.debug("count: {}", count);
    List<Integer> list =
        IntStream.range(0, this.puzzle.size())
            .filter(x -> this.puzzle.get(x).contains(MAP))
            .boxed()
            .toList();
    log.debug("list: {}", list);
    return IntStream.range(0, list.size())
        .mapToObj(
            i -> {
              Integer start = list.get(i);
              log.debug("start of the line: {}", start);
              if (i < list.size() - 1) {
                return lineOfMap(start, list.get(i + 1));
              } else {
                return lineOfMap(start, puzzle.size() + 1);
              }
            })
        .toList();
  }

  @Override
  public long solvePart01() {
    log.debug("seeds: {}", this.seeds);
    return this.puzzle.size();
  }

  private Seeds parseSeeds(String input) {
    String[] split = input.split(":");

    return new Seeds(Arrays.stream(split[1].trim().split("\\s+")).map(Long::parseLong).toList());
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/05/2023_05_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day05 day = new Day05(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  public List<LineOfMap> lineOfMap(int start, int end) {
    log.debug("start: {}", start);
    log.debug("end: {}", end);

    return IntStream.range(start + 1, end - 1)
        .mapToObj(
            x -> {
              String line = this.puzzle.get(x);
              log.debug("line: {}", line);
              return parseLine(line);
            })
        .toList();
  }

  LineOfMap parseLine(String line) {
    String[] s = line.split(" ");
    long destination = Long.parseLong(s[0]);
    long source = Long.parseLong(s[1]);
    long range = Long.parseLong(s[2]);
    return new LineOfMap(destination, source, range);
  }

  public record LineOfMaps(List<LineOfMap> lineOfMaps) {}

  public record LineOfMap(long destination, long source, long range) {}

  public record Seeds(List<Long> list) {}
}
