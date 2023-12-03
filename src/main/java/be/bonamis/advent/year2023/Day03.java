package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day03 extends DaySolver<String> {

  public Day03(List<String> puzzle) {
    super(puzzle);
  }

  static boolean isSymbol(Character character) {
    char dot = '.';
    boolean isNumberOrDot = Character.isDigit(character) || character == dot;
    return !isNumberOrDot;
  }

  record Engine(List<Point> points) {
    boolean isPartNumber(CharGrid grid) {
      return this.points.stream()
          .flatMap(point -> grid.neighbours(point).stream())
          .filter(o -> !this.points.contains(o))
          .anyMatch(o -> isSymbol(grid.get(o)));
    }

    boolean isNotPartNumber(CharGrid grid) {
      return !this.isPartNumber(grid);
    }

    public long number(CharGrid grid) {
      return this.points.stream()
          .map(grid::get)
          .mapToInt(Character::getNumericValue)
          .reduce(0, (acc, digit) -> acc * 10 + digit);
    }
  }

  @Override
  public long solvePart01() {
    return this.puzzle.size();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/03/2023_03_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day03 day = new Day03(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
