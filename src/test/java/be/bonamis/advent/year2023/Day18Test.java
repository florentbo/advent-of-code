package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

import be.bonamis.advent.year2023.Day18.Dig;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day18Test {

  private Day18 day18;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/18/2023_18_00_code.txt");
    day18 = new Day18(Arrays.asList(content.split("\n")), 30);
  }

  @Test
  void solvePart01() {
    assertThat(day18.solvePart01()).isEqualTo(62);
  }

  @Test
  void paint() {
    day18 = new Day18(Arrays.asList("".split("\n")), 10);
    Dig dig01 = new Dig(Dig.Direction.RIGHT, 3, "(#70c710)");
    Dig dig02 = new Dig(Dig.Direction.DOWN, 2, "(#70c710)");
    Dig dig03 = new Dig(Dig.Direction.LEFT, 3, "(#70c710)");
    Dig dig04 = new Dig(Dig.Direction.UP, 2, "(#70c710)");
    day18.createGrid(List.of(dig01, dig02, dig03, dig04));

    assertThat(day18.paintRow(0)).hasSize(10);
    assertThat(day18.paintRow(day18.getGrid().getHeight() - 1)).hasSize(10);
    assertThat(day18.paintRow(5)).hasSize(6);

    assertThat(day18.paintColumn(0)).hasSize(10);
    assertThat(day18.paintColumn(day18.getGrid().getWidth() - 1)).hasSize(10);
    assertThat(day18.paintColumn(5)).hasSize(7);

    assertThat(day18.painted()).isEqualTo(10 * 10 - 3 * 2);
  }

  @Test
  void parse() {
    Dig dig = Dig.parse("R 6 (#70c710)");
    assertThat(dig).isEqualTo(new Dig(Dig.Direction.RIGHT, 6, "(#70c710)"));
  }
}
