package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day13 extends DaySolver<String> {

  private List<CharGrid> grids;

  public Day13(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    grids();
    CharGrid grid = this.grids.get(0);
    //linesHandling(grid);

    List<List<Point>> columns = grid.columns();
    List<Point> column = columns.get(0);
    log.debug("columns: {}", column);
    List<String> collect = columns.stream().map(points -> toLine(points, grid)).toList();
    log.debug("collect: {}", collect);
    findCommonElementsAndIndices(collect).forEach((key, value) -> log.debug("key: {}, value: {}", key, value));


    /*for (List<Point> line : lines) {
      Stream<Character> characterStream = line.stream().map(grid::get);
      String collect1 = characterStream.map(String::valueOf).collect(Collectors.joining());
      log.debug("collect1: {}", collect1);
    }*/

    return this.puzzle.size();
  }

  private void linesHandling(CharGrid grid) {
    List<List<Point>> lines = grid.lines();
    List<String> collect = lines.stream().map(points -> toLine(points, grid)).toList();
    log.debug("collect: {}", collect);
    findCommonElementsAndIndices(collect).forEach((key, value) -> log.debug("key: {}, value: {}", key, value));
  }

  Map<String, List<Integer>> findCommonElementsAndIndices(List<String> list) {
    Map<String, List<Integer>> elementIndicesMap = new HashMap<>();

    for (int i = 0; i < list.size(); i++) {
      String element = list.get(i);
      elementIndicesMap.computeIfAbsent(element, k -> new ArrayList<>()).add(i);
    }

    elementIndicesMap.entrySet().removeIf(entry -> entry.getValue().size() < 2);

    return elementIndicesMap;
  }

  private String toLine(List<Point> line, CharGrid grid) {
    Stream<Character> characterStream = line.stream().map(grid::get);
    return characterStream.map(String::valueOf).collect(Collectors.joining());
  }

  private void grids() {
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

    grids =
        IntStream.range(1, numbers.size())
            .mapToObj(i -> createGrid(numbers.get(i - 1), numbers.get(i)))
            .toList();

    log.debug("grids: {}", grids);
  }

  CharGrid createGrid(int start, int end) {
    List<String> collect = IntStream.range(start, end - 1).mapToObj(this.puzzle::get).toList();
    CharGrid grid = new CharGrid(collect.stream().map(String::toCharArray).toArray(char[][]::new));
    for (List<Point> line : grid.lines()) {
      Stream<Character> characterStream = line.stream().map(grid::get);
      String collect1 = characterStream.map(String::valueOf).collect(Collectors.joining());
      // log.debug("collect1: {}", collect1);
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
