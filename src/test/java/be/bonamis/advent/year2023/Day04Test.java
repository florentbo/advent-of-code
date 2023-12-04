package be.bonamis.advent.year2023;

import static java.lang.Character.isDigit;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.utils.FileHelper;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
class Day04Test {

  private Day04 day03;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/04/2023_04_01_code.txt");
    day03 = new Day04(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    assertThat(day03.solvePart01()).isEqualTo(13);
  }

  @Test
  void solvePart02() {
    assertThat(day03.solvePart02()).isEqualTo(7);
  }
}
