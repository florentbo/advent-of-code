package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day17Test {

  private Day17 day17;

  @BeforeEach
  void setUp() {}

  @Test
  void solvePart01_2021Day15() {
    // String content = FileHelper.content("2023/17/2023_17_00_code.txt");
    String content = FileHelper.content("2023/17/2021_day15_test.txt");
    day17 = new Day17(Arrays.asList(content.split("\n")));
    assertThat(day17.solvePart01()).isEqualTo(40);
  }

  @Test
  void solvePart01_2021Day15CustomAlgo() {
    String content = FileHelper.content("2023/17/2021_day15_test.txt");
    day17 = new Day17(Arrays.asList(content.split("\n")));
    assertThat(day17.solveShortestPathDijkstra(false)).isEqualTo(40);
  }

  @Test@Disabled("not working yet")
  void solvePart01() {
    String content = FileHelper.content("2023/17/2023_17_00_code.txt");
    day17 = new Day17(Arrays.asList(content.split("\n")));
    assertThat(day17.solveShortestPathDijkstra(true)).isEqualTo(102);
  }
}
