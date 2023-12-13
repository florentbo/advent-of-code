package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day13 extends DaySolver<String> {

  public Day13(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    List<Integer> emptyLines =
        IntStream.range(0, this.puzzle.size())
            .filter(index -> this.puzzle.get(index).isEmpty())
            .mapToObj(index -> index + 1)
            .toList();
    log.debug("emptyLines: {}", emptyLines);

    List<Integer> numbers = new ArrayList<>();
    numbers.add(0);
    numbers.addAll(emptyLines);
    numbers.add(this.puzzle.size() + 1);
    log.debug("numbers: {}", numbers);

    List<CharGrid> grids =
        IntStream.range(1, numbers.size())
            .mapToObj(i -> createGrid(numbers.get(i - 1), numbers.get(i)))
            .toList();

    log.debug("grids: {}", grids);
    return this.puzzle.size();
  }

  CharGrid createGrid(int start, int end) {
    List<String> collect = IntStream.range(start, end - 1).mapToObj(this.puzzle::get).toList();
    CharGrid grid = new CharGrid(collect.stream().map(String::toCharArray).toArray(char[][]::new));
    for (List<Point> line : grid.lines()) {
      Stream<Character> characterStream = line.stream().map(grid::get);
      String collect1 = characterStream.map(String::valueOf).collect(Collectors.joining());
      log.debug("collect1: {}", collect1);
    }

    return grid;
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/13/2023_13_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day13 day = new Day13(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    // log.info("solution part 2: {}", day.solvePart02());
  }
}
