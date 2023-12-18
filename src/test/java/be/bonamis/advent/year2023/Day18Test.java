package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;

import be.bonamis.advent.year2023.Day18.Dig;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day18Test {

  private Day18 day18;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/18/2023_18_00_code.txt");
    day18 = new Day18(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    assertThat(day18.solvePart01()).isEqualTo(62);
  }

  @Test
  void parse() {
    Dig dig = Dig.parse("R 6 (#70c710)");
    assertThat(dig).isEqualTo(new Dig(Dig.Direction.RIGHT, 6, "(#70c710)"));
  }
}
