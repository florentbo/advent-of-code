package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
class Day06Test {

  private Day06 day06;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/04/2023_04_01_code.txt");
    day06 = new Day06(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    assertThat(day06.solvePart01()).isEqualTo(6);
  }

  @Test
  void solvePart02() {
    assertThat(day06.solvePart02()).isEqualTo(7);
  }
}
