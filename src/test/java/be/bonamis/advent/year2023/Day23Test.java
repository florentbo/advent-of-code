package be.bonamis.advent.year2023;

import be.bonamis.advent.utils.FileHelper;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

  private Day23 day;

  @Test@Disabled("not solved yet")
  void solvePart01() {
    String content = FileHelper.content("2023/23/2023_23_06_code.txt");
    day = new Day23(Arrays.asList(content.split("\n")));
    assertThat(day.solvePart01()).isEqualTo(94);
  }
}
