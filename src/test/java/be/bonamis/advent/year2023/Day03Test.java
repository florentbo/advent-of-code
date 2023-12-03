package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.utils.FileHelper;
import java.util.*;
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
  void isSymbol() {
    assertThat(Day03.isSymbol('*')).isTrue();
    assertThat(Day03.isSymbol('7')).isFalse();
    assertThat(Day03.isSymbol('.')).isFalse();
  }
}
