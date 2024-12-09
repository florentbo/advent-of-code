package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.common.CharGrid;
import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day09 extends TextDaySolver {


  public Day09(InputStream inputStream) {
    super(inputStream);
  }

  public Day09(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    log.info("size: {}", this.puzzle.size());
    String line = this.puzzle.get(0);
    log.info("line: {}", line);
    int length = line.length();
    log.info("line length: {}", length);

    //12345 ---> 0..111....22222
    List<Integer> list = line.chars().mapToObj(Character::toString).map(Integer::parseInt).toList();
    log.info("list: {}", list);


    return 3;
  }

  @Override
  public long solvePart02() {
    return 4;
  }
}
