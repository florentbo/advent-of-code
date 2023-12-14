package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day14 extends DaySolver<String> {

  private final CharGrid grid;

  public Day14(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    List<List<Point>> columns = grid.columns();
    return columns.stream().flatMap(col -> rocks(col).stream()).reduce(Long::sum).orElseThrow();
  }

  List<Long> rocks(List<Point> column) {
    int size = column.size();
    log.debug("size: {}", size);

    List<String> values =
        column.stream()
            .map(grid::get)
            .map(Object::toString)
            .toList(); // Convert each character to its string representation

    log.debug("values: {}", values);
    Map<Integer, String> map = listToMap(values);
    log.debug("map: {}", map);
    long rockHeight = size + 1;
    List<Long> rocks = new ArrayList<>();
    for (Map.Entry<Integer, String> mapEntry : map.entrySet()) {
      log.debug("mapEntry: {}", mapEntry);
      int key = mapEntry.getKey();
      String value = mapEntry.getValue();
      log.debug("rockHeight: {}", rockHeight);
      if (value.equals("O")) {
        rockHeight--;
        rocks.add(rockHeight);
        log.debug("rocks: {}", rocks);
      }
      if (value.equals("#")) {
        rockHeight = key;
        log.debug("rocks: {}", rocks);
      }
    }
    log.debug("rocks: {}", rocks);
    return rocks;
  }

  Map<Integer, String> listToMap(List<String> originalArray) {
    TreeMap<Integer, String> map = new TreeMap<>(Collections.reverseOrder());

    int position = originalArray.size();

    for (String character : originalArray) {
      if (!character.equals(".")) {
        map.put(position, character);
      }
      position--;
    }

    return map;
  }

  private Optional<String> last(List<String> moved) {
    int size = moved.size();
    return size > 1 ? Optional.of(moved.get(size - 1)) : Optional.empty();
  }

  @Override
  public long solvePart02() {
    return 0;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/14/2023_14_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day14 day = new Day14(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
