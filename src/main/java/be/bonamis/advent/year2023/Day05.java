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

  private final Seeds seeds;

  public Day05(List<String> puzzle) {
    super(puzzle);
    this.seeds = parseSeeds(this.puzzle.get(0));

    long count =
        IntStream.range(0, this.puzzle.size())
            .filter(x -> this.puzzle.get(x).contains(" map:"))
            .count();
    log.debug("count: {}", count);
    IntStream intStream =
        IntStream.range(0, this.puzzle.size())
            .filter(x -> this.puzzle.get(x).contains(" map:"))
            .limit(count - 1);
    List<List<LineOfMap>> listList = intStream.mapToObj(i -> lineOfMap(i, i + 1)).toList();

    log.debug("list: {}", listList);
  }

  @Override
  public long solvePart01() {
    log.debug("seeds: {}", this.seeds);
    return this.puzzle.size();
  }

  public Seeds getSeeds() {
    return seeds;
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
