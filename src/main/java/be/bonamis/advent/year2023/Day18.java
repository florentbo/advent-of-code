package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.EAST;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
@Getter
public class Day18 extends DaySolver<String> {

  private CharGrid grid;

  public Day18(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    List<Dig> puzzle = this.puzzle.stream().map(Dig::parse).toList();
    this.puzzle.forEach(System.out::println);
    return 999;
  }

  @Override
  public long solvePart02() {
    return 888;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/18/2023_18_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day18 day = new Day18(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  record Dig(String direction, long meters, String color) {
    public static Dig parse(String line) {
      String[] parts = line.split(" ");
      String direction = parts[0];
      String color = parts[2];
      long meters = Long.parseLong(parts[1]);
      return new Dig(direction, meters, color);
    }
  }
}
