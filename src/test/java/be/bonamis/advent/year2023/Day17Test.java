package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day17Test {

  private Day17 day17;

  @BeforeEach
  void setUp() {
    // String content = FileHelper.content("2023/17/2023_17_00_code.txt");
    String content = FileHelper.content("2023/17/2021_day15_test.txt");
    // String content = FileHelper.content("2023/17/2023_17_00_code.txt");
    day17 = new Day17(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    assertThat(day17.solvePart01()).isEqualTo(40);
  }

  @Test
  void solvePart01Simple() {
    assertThat(day17.solveSimplePart01()).isEqualTo(40);
  }
}
