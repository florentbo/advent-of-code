package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;

import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day21Test {

  private Day21 day;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/21/2023_21_04_code.txt");
    day = new Day21(Arrays.asList(content.split("\n")), 6);
  }

  @Test
  void solvePart01() {
    assertThat(day.solvePart01()).isEqualTo(16);
  }

  @Test
  void startPosition() {
    assertThat(day.startPosition()).isEqualTo(new Point(5, 5));
  }

  @Test
  void steps() {
    assertThat(day.steps(Set.of(day.startPosition())))
        .containsExactlyInAnyOrder(new Point(4, 5), new Point(5, 4));
  }

  @Test
  void multiSteps() {
    assertThat(day.multiSteps(1, Set.of(day.startPosition())))
        .containsExactlyInAnyOrder(new Point(4, 5), new Point(5, 4), new Point(5, 5));

    assertThat(day.multiSteps(2, Set.of(day.startPosition())))
        .hasSize(4)
        .contains(new Point(3, 5), new Point(5, 5));

    assertThat(day.multiSteps(3, Set.of(day.startPosition()))).hasSize(7);

    assertThat(day.multiSteps(6, Set.of(day.startPosition()))).hasSize(16);
  }
}
