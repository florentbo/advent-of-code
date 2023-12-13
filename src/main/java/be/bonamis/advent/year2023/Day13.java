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
    gridsInit();
  }

  @Override
  public long solvePart01() {

    // CharGrid charGrid = this.grids.get(1);
    // int linesHandling = linesHandling(charGrid);

    int lines = this.grids.stream().map(this::linesHandling).reduce(Integer::sum).orElseThrow();
    int columns = this.grids.stream().map(this::columnHandling).reduce(Integer::sum).orElseThrow();

    /*for (CharGrid charGrid : this.grids) {
      int columnedHandling = columnHandling(charGrid);

      int linesHandling = linesHandling(charGrid);
    }*/

    return lines * 100L + columns;
  }

  private int columnHandling(CharGrid grid) {
    log.debug("columnHandling");
    List<List<Point>> gridColumns = grid.columns();
    List<String> columns = gridColumns.stream().map(points -> toColumn(points, grid)).toList();

    return commonElementsResult(columns, grid.getWidth());
  }

  private int commonElementsResult(List<String> columns, int width) {
    Map<String, List<Integer>> commonElement = findCommonElementsAndIndices(columns);

    List<Integer> middle =
        commonElement.values().stream()
            .filter(list -> (list.get(1) - list.get(0) == 1))
            .findFirst()
            .orElseThrow();
    int middleLeft = middle.get(0);
    int middleRight = middle.get(1);
    int end = width - middleRight - 1;
    int min = Math.min(middleLeft, end);

    /* log.debug("middle: {}", middle);
    log.debug("width: {}", width);
    log.debug("end: {}", end);
    log.debug("min: {}", min);*/

    /* IntStream.range(0, min)
        .mapToObj(index -> columns.get(middleLeft + 2 + index))
        .toList()
        .forEach(log::debug);

    IntStream.range(0, min)
        .mapToObj(index -> columns.get(middleLeft - 1 - index))
        .toList()
        .forEach(log::debug);*/

    boolean allMatch =
        IntStream.range(0, min)
            .allMatch(
                index -> {
                  int rightIndex = middleLeft + 2 + index;
                  int leftIndex = middleLeft - 1 - index;
                  return rightIndex >= 0
                      && rightIndex < columns.size()
                      && columns.get(rightIndex).equals(columns.get(leftIndex));
                });
    // log.debug("allMatch: {}", allMatch);

    int result = allMatch ? middleRight : 0;
    log.debug("result: {}", result);

    // commonElement.forEach((key, value) -> log.debug("key: {}, value: {}", key, value));
    return result;
  }

  private int linesHandling(CharGrid grid) {
    log.debug("linesHandling");
    List<List<Point>> lines = grid.lines();
    List<String> collect = lines.stream().map(points -> toLine(points, grid)).toList();
    return commonElementsResult(collect, grid.getHeight());
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

  private String toColumn(List<Point> line, CharGrid grid) {
    Stream<Character> characterStream = line.stream().map(grid::get2);
    return characterStream.map(String::valueOf).collect(Collectors.joining());
  }

  private void gridsInit() {
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
    /*for (List<Point> line : grid.lines()) {
      Stream<Character> characterStream = line.stream().map(grid::get);
      String collect1 = characterStream.map(String::valueOf).collect(Collectors.joining());
      // log.debug("collect1: {}", collect1);
    }*/
    log.debug("created grid: {}", grid);
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
