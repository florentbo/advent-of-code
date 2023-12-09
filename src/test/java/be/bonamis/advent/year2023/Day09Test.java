package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.utils.FileHelper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
class Day09Test {

  private Day09 day09;

  @Test
  void solvePart01() {

    String text = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
""";
    day09 = new Day09(Arrays.asList(text.split("\n")));

    assertThat(day09.solvePart01()).isEqualTo(114);
  }

  @Test
  void solveLine() {
    String text = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
""";
    day09 = new Day09(Arrays.asList(text.split("\n")));
    assertThat(day09.solveLine("10  13  16  21  30  45")).isEqualTo(68);
  }

  /*  @Test
    void solveLineInput() {
      String text = """
  0 3 6 9 12 15
  1 3 6 10 15 21
  10 13 16 21 30 45
  """;
      day09 = new Day09(Arrays.asList(text.split("\n")));
      assertThat(
              day09.solveLine(
                  "-4 4 20 51 110 210 353 506 565 334 -395 -1456 -1197 6132 35806 122961 341002 832656 1860042 3882361 7672508"))
          .isEqualTo(68);
    }*/

  @Test
  void solvePart02() {
    String text = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
""";
    day09 = new Day09(Arrays.asList(text.split("\n")));
    assertThat(day09.solveLine2("10  13  16  21  30  45")).isEqualTo(5);
    ;
  }
}
