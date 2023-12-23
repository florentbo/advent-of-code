package be.bonamis.advent.year2023;

import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.year2021.Day21;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

  private Day23 day;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/23/2023_23_06_code.txt");
    day = new Day23(Arrays.asList(content.split("\n")));
  }

  @Test@Disabled("Not solved yet")
  void solvePart01() {
    assertThat(day.solvePart01()).isEqualTo(94);
  }

  @Test
  void isNotForest() {
    assertThat(day.isNotForest(Position.of(1, 0).toPoint())).isTrue();
  }

  @Test
  void isForest() {
    assertThat(day.isForest(Position.of(0, 0).toPoint())).isTrue();
  }
}
