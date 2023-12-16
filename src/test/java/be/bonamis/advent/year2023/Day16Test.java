package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.common.CharGrid;
import java.util.*;
import java.util.List;

import be.bonamis.advent.utils.FileHelper;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

@Slf4j
class Day16Test {

  private Day16 day16;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/16/2023_16_05_code.txt");
    day16 = new Day16(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    assertThat(day16.solvePart01()).isEqualTo(46);
  }

  @Test
  void solvePart02() {
    assertThat(day16.solvePart02()).isEqualTo(51);
  }

  @Test
  void edges() {
    CharGrid grid = day16.getGrid();
    assertThat(grid.topEdge().count()).isEqualTo(grid.getWidth());
    assertThat(grid.bottomEdge().count()).isEqualTo(grid.getWidth());
    assertThat(grid.leftEdge().count()).isEqualTo(grid.getHeight());
    assertThat(grid.rightEdge().count()).isEqualTo(grid.getHeight());
  }
}
