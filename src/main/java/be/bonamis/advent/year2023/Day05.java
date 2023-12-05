package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

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
    List<Integer> list =
        new ArrayList<>(
            IntStream.range(0, this.puzzle.size())
                .filter(x -> this.puzzle.get(x).contains(MAP))
                .boxed()
                .toList());
    list.add(puzzle.size() + 1); // end of file line

    log.debug("list: {}", list);
    return IntStream.range(0, list.size() - 1)
        .mapToObj(i -> lineOfMap(list.get(i), list.get(i + 1)))
        .toList();
  }

  @Override
  public long solvePart01() {
    log.debug("seeds: {}", this.seeds);
    return this.seeds.list().stream()
        .mapToLong(seed -> location(this.lineMaps, seed))
        .min()
        .orElseThrow();
  }

  private Seeds parseSeeds(String input) {
    List<Long> originalSeeds =
        Arrays.stream(input.split(":")[1].trim().split("\\s+")).map(Long::parseLong).toList();
    log.info("seeds: {}  size: {}", originalSeeds, originalSeeds.size());
    return new Seeds(originalSeeds);
  }

  long location(List<List<LineOfMap>> lineMaps, Long seed) {
    long initialSeed = seed;
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

    return IntStream.range(0, originalSeeds.size())
        .filter(idx -> idx % 2 == 0)
        .mapToObj(idx -> Pair.of(originalSeeds.get(idx), originalSeeds.get(idx + 1)))
        .parallel()
        .mapToLong(this::findMin)
        .min()
        .orElseThrow();
  }

  private long findMin(Pair<Long, Long> pair) {
    long min = Long.MAX_VALUE;
    long start = pair.getLeft();
    long range = pair.getRight();
    log.info("seed 01: {}  seed02: {}", start, range);
    for (long seed = start; seed < start + range; seed++) {
      long location = location(this.lineMaps, seed);
      if (location < min) {
        min = location;
      }
    }
    return min;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/05/2023_05_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    log.info("solution part 1: {}", new Day05(puzzle).solvePart01());
    log.info("solution part 2: {}", new Day05(puzzle).solvePart02());
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
