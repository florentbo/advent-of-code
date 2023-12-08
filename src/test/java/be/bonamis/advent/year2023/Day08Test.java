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
    day08 = new Day08(Arrays.asList(FileHelper.content("2023/08/2023_08_20_code.txt").split("\n")));
    assertThat(day08.solvePart01()).isEqualTo(6);
  }

  @Test
  void solvePart02() {
    day08 = new Day08(Arrays.asList(FileHelper.content("2023/08/2023_08_04_code.txt").split("\n")));
    assertThat(day08.solvePart02()).isEqualTo(10);
  }
}
