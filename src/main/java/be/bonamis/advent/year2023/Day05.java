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

  public Day05(List<String> puzzle, boolean rangeOfSeeds) {
    super(puzzle);
    this.seeds = parseSeeds(this.puzzle.get(0), rangeOfSeeds);
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
    return this.seeds.list().stream()
        .map(seed -> location(this.lineMaps, seed))
        .min(Long::compare)
        .orElseThrow();
  }

  private Seeds parseSeeds(String input, boolean rangeOfSeeds) {
    List<Long> originalSeeds =
        Arrays.stream(input.split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
    log.info("seeds: {}  size: {}", originalSeeds, originalSeeds.size());
   /* if (rangeOfSeeds) {
      List<Long> rangedSeeds = new ArrayList<>();
      for (int i = 0; i < originalSeeds.size(); i += 2) {
        Long start = originalSeeds.get(i);
        Long range = originalSeeds.get(i + 1);
        log.info("seed 01: {}  seed02: {}", start, range);
        for (long j = start; j < start + range; j++) {
          // rangedSeeds.add(j);
        }
      }
      // 194.657.215
      // 187.012.821
      log.info("seeds: {}  size: {}", rangedSeeds, rangedSeeds.size());
      return new Seeds(rangedSeeds);
    }*/
    return new Seeds(originalSeeds);
  }

  Long location(List<List<LineOfMap>> lineMaps, Long seed) {
    Long initialSeed = seed;
    for (List<LineOfMap> lineMap : lineMaps) {
      initialSeed = correspond(lineMap, initialSeed);
    }
    return initialSeed;
  }

  Long correspond(List<LineOfMap> lines, Long seed) {
    return lines.stream()
        .filter(
            line -> {
              long start = line.source();
              long end = line.source() + line.range();
              return (seed >= start && seed <= end);
            })
        .findFirst()
        .map(line -> seed + line.destination() - line.source())
        .orElse(seed);
  }

  @Override
  public long solvePart02() {
    List<Long> originalSeeds = this.seeds.list();
    long min = Long.MAX_VALUE;

    for (int i = 0; i < originalSeeds.size(); i += 2) {
      Long start = originalSeeds.get(i);
      Long range = originalSeeds.get(i + 1);
      log.info("seed 01: {}  seed02: {}", start, range);
      for (long seed = start; seed < start + range; seed++) {
        Long location = location(this.lineMaps, seed);
        if (location < min) {
          min = location;
        }
      }
    }

    log.debug("seeds: {}", originalSeeds);
    return min;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/05/2023_05_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    log.info("solution part 1: {}", new Day05(puzzle, false).solvePart01());
    log.info("solution part 2: {}", new Day05(puzzle, true).solvePart02());
  }

  public List<LineOfMap> lineOfMap(int start, int end) {
    return IntStream.range(start + 1, end - 1)
        .mapToObj(x -> parseLine(this.puzzle.get(x)))
        .toList();
  }

  LineOfMap parseLine(String line) {
    String[] s = line.split(" ");
    long destination = Long.parseLong(s[0]);
    long source = Long.parseLong(s[1]);
    long range = Long.parseLong(s[2]);
    return new LineOfMap(destination, source, range);
  }

  public record LineOfMap(long destination, long source, long range) {}

  public record Seeds(List<Long> list) {}
}
