package be.bonamis.advent.year2023;

import be.bonamis.advent.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day08Test {

  private Day08 day08;

  @BeforeEach
  void setUp() {
    // String content = FileHelper.content("2023/08/2023_08_20_code.txt");

  }

  @Test
  void solvePart01() {
    day08 = new Day08(Arrays.asList(FileHelper.content("2023/08/2023_08_04_code.txt").split("\n")));
    assertThat(day08.solvePart01()).isEqualTo(2);

    String text = """
LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)
""";
    day08 = new Day08(Arrays.asList(text.split("\n")));

    assertThat(day08.solvePart01()).isEqualTo(6);
  }

  @Test
  void solvePart02() {
    String text = """
LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)
""";
    day08 = new Day08(Arrays.asList(text.split("\n")));
    assertThat(day08.solvePart02()).isEqualTo(6);
  }
}
