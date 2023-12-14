package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.CollectionsHelper;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
@Getter
public class Day13 extends DaySolver<String> {
  private final List<CharGrid> grids;

  public Day13(List<String> puzzle) {
    super(puzzle);
    this.grids = gridsInit();
  }

  @Override
  public long solvePart01() {
    return solve(false);
  }

  private long solve(boolean withSmudge) {
    int columns =
        CollectionsHelper.sum(this.grids.stream().map(grid -> columnHandling(grid, withSmudge)));
    int lines =
        CollectionsHelper.sum(this.grids.stream().map(grid -> linesHandling(grid, withSmudge)));

    return lines * 100L + columns;
  }

  @Override
  public long solvePart02() {
    return solve(true);
  }

  int columnHandling(CharGrid grid, boolean withSmudge) {
    int previous = columnResult(grid);
    return withSmudge ? smudgeResult(grid, columnHandling, previous) : previous;
  }

  private int columnResult(CharGrid grid) {
    log.debug("columnHandling");
    List<List<Point>> gridColumns = grid.columns();
    List<String> columns = gridColumns.stream().map(points -> toColumn(points, grid)).toList();

    return commonElementsResult(columns, grid.getWidth());
  }

  private int commonElementsResult(List<String> columns, int width) {
    Map<String, List<Integer>> commonElement = findCommonElementsAndIndices(columns);

    List<Pair<Integer, Integer>> middles = findMiddles(commonElement.values());
    return middles.stream()
        .map(pair -> toResult(pair, columns, width))
        .filter(i -> i != 0)
        .findFirst()
        .orElse(0);
  }

  public List<Pair<Integer, Integer>> findMiddles(Collection<List<Integer>> lists) {
    return lists.stream().flatMap(this::findMiddles).toList();
  }

  private Stream<Pair<Integer, Integer>> findMiddles(List<Integer> values) {
    return IntStream.range(1, values.size())
        .filter(i -> values.get(i) - values.get(i - 1) == 1)
        .mapToObj(i -> Pair.of(values.get(i - 1), values.get(i)));
  }

  private int toResult(Pair<Integer, Integer> middle, List<String> columns, int width) {
    int middleLeft = middle.getLeft();
    int middleRight = middle.getRight();
    int end = width - middleRight - 1;
    int min = Math.min(middleLeft, end);

    log.debug("middle: {}", middle);
    log.debug("width: {}", width);
    log.debug("end: {}", end);
    log.debug("min: {}", min);
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
    log.debug("allMatch: {}", allMatch);

    int result = allMatch ? middleRight : 0;
    log.debug("result: {}", result);
    return result;
  }

  int linesHandling(CharGrid grid, boolean withSmudge) {
    int lineResult = lineResult(grid);
    return withSmudge ? smudgeResult(grid, linesHandling, lineResult) : lineResult;
  }

  private Integer smudgeResult(
      CharGrid grid, Function<CharGrid, Integer> handling, int previousResult) {
    return grid.stream()
        .map(point -> toGrid(point, grid, handling))
        .filter(res -> isaBoolean(previousResult, res))
        .findFirst()
        .orElse(0);
  }

  private boolean isaBoolean(int previousResult, Integer res) {
    boolean b = res != previousResult && res != 0;
    return b;
  }

  int lineResult(CharGrid grid) {
    log.debug("linesHandling");
    List<List<Point>> lines = grid.lines();
    List<String> collect = lines.stream().map(points -> toLine(points, grid)).toList();
    return commonElementsResult(collect, grid.getHeight());
  }

  private int toGrid(Point point, final CharGrid grid, Function<CharGrid, Integer> handling) {
    Character c = grid.get(point);
    CharGrid newGrid =
        new CharGrid(Arrays.stream(grid.getData()).map(char[]::clone).toArray(char[][]::new));
    newGrid.set(point, c == '.' ? '#' : '.');
    return handling.apply(newGrid);
  }

  Function<CharGrid, Integer> linesHandling = this::lineResult;

  Function<CharGrid, Integer> columnHandling = this::columnResult;

  Map<String, List<Integer>> findCommonElementsAndIndices(List<String> list) {
    Map<String, List<Integer>> elementIndicesMap = new HashMap<>();

    for (int i = 0; i < list.size(); i++) {
      String element = list.get(i);
      elementIndicesMap.computeIfAbsent(element, k -> new ArrayList<>()).add(i);
    }

    elementIndicesMap.entrySet().removeIf(entry -> entry.getValue().size() < 2);
    log.debug("elementIndicesMap: {}", elementIndicesMap);

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

  private List<CharGrid> gridsInit() {
    List<Integer> emptyLines =
        IntStream.range(0, this.puzzle.size())
            .filter(index -> this.puzzle.get(index).isEmpty())
            .mapToObj(index -> index + 1)
            .toList();
    log.debug("emptyLines: {}", emptyLines);
    for (int i = 0; i < emptyLines.size(); i++) {
      log.info("emptyLines {}: {}", i, emptyLines.get(i));
    }
    log.info("emptyLines size: {}", emptyLines.size());

    List<Integer> numbers = new ArrayList<>();
    numbers.add(0);
    numbers.addAll(emptyLines);
    numbers.add(this.puzzle.size() + 1);
    log.debug("numbers: {}", numbers);

    List<CharGrid> charGrids =
        IntStream.range(1, numbers.size())
            .mapToObj(i -> createGrid(numbers.get(i - 1), numbers.get(i)))
            .toList();
    log.debug("grids: {}", charGrids);
    log.info("grids size: {}", charGrids.size());
    return charGrids;
  }

  CharGrid createGrid(int start, int end) {
    List<String> collect = IntStream.range(start, end - 1).mapToObj(this.puzzle::get).toList();
    CharGrid grid = new CharGrid(collect.stream().map(String::toCharArray).toArray(char[][]::new));
    log.debug("created grid: {}", grid);
    return grid;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/13/2023_13_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day13 day = new Day13(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}