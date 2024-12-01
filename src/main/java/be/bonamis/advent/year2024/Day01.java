package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Slf4j
public class Day01 extends TextDaySolver {

  public Day01(InputStream inputStream) {
    super(inputStream);
  }

  public Day01(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    List<Integer> lefts =
        this.puzzle.stream().map(s -> Integer.parseInt(s.split("\\s+")[0])).toList();
    List<Integer> rights =
        this.puzzle.stream().map(s -> Integer.parseInt(s.split("\\s+")[1])).toList();
    log.debug("lefts: {}", lefts);
    log.debug("rights: {}", rights);

    List<Integer> sortedLefts = lefts.stream().sorted().toList();
    List<Integer> sortedRights = rights.stream().sorted().toList();
    log.debug("sortedLefts: {}", sortedLefts);
    log.debug("sortedRights: {}", sortedRights);

    List<Integer> differences =
        IntStream.range(0, sortedLefts.size())
            .mapToObj(i -> Math.abs(sortedLefts.get(i) - sortedRights.get(i)))
            .toList();

    return differences.stream().reduce(0, Integer::sum);
  }

  @Override
  public long solvePart02() {
    List<Integer> lefts =
        this.puzzle.stream().map(s -> Integer.parseInt(s.split("\\s+")[0])).toList();
    List<Integer> rights =
        this.puzzle.stream().map(s -> Integer.parseInt(s.split("\\s+")[1])).toList();

    return lefts.stream()
        .map(
            left ->
                IntStream.range(0, rights.size())
                        .filter(i -> Objects.equals(rights.get(i), left))
                        .count()
                    * left)
        .reduce(0L, Long::sum);
  }
}
