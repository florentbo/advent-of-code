package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day12 extends TextDaySolver {

  private final CharGrid grid;

  public Day12(InputStream inputStream) {
    super(inputStream);
    grid = new CharGrid(this.puzzle);
  }

  public Day12(List<String> puzzle) {
    super(puzzle);
    grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    return 1;
  }

  @Override
  public long solvePart02() {
    return 2;
  }

  public CharGrid grid() {
    return grid;
  }
}
