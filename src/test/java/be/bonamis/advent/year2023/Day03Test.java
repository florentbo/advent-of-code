package be.bonamis.advent.year2023;

import static java.lang.Character.isDigit;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
class Day03Test {

  private Day03 day03;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/03/2023_03_01_code.txt");
    day03 = new Day03(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    assertThat(day03.solvePart01()).isEqualTo(4361L);
  }

  @Test
  void solvePart02() {
    assertThat(day03.solvePart02()).isEqualTo(467835L);
  }

  @Test
  void name() {
    CharGrid grid = day03.getGrid();
    List<Point> points = grid.lines().get(0);
    Stream<Day03.Engine> engineStream = points.stream()
            .filter(o -> isDigit(grid.get(o)))
            .collect(Collectors.groupingBy(p -> p))
            .values()
            .stream()
            .map(Day03.Engine::new);
    log.info("{}", engineStream.collect(Collectors.toList()));
  }

  @Test
  void isSymbol() {
    assertThat(Day03.isSymbol('*')).isTrue();
    assertThat(Day03.isSymbol('7')).isFalse();
    assertThat(Day03.isSymbol('.')).isFalse();
  }
}
