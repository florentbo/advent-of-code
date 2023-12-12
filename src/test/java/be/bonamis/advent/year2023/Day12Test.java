package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import java.util.List;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day12Test {

  private final List<String> input =
      Arrays.asList(
          """
???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
                  """
              .split("\n"));
  private final Day12 day12 = new Day12(input);

  @Test
  void solvePart01() {
    assertThat(day12.solvePart01()).isEqualTo(21);
  }

  @Test
  void damageCountTest() {
    String input = "#.#.###";
    assertThat(day12.damageCount(input)).isEqualTo(List.of(1, 1, 3));
  }

  @Test
  void solveRow() {
    assertThat(day12.solveRow("???.### 1,1,3")).isEqualTo(1);
    String row00 = input.get(0);
    String row01 = input.get(1);

    assertThat(day12.solveRow(row00)).isEqualTo(1);
    assertThat(day12.solveRow(row01)).isEqualTo(4);
    assertThat(day12.solveRow(input.get(2))).isEqualTo(1);
    assertThat(day12.solveRow(input.get(3))).isEqualTo(1);
    assertThat(day12.solveRow(input.get(4))).isEqualTo(4);
    assertThat(day12.solveRow(input.get(5))).isEqualTo(10);

    assertThat(day12.solveRow2(day12.unfold(row00))).isEqualTo(1);
    //assertThat(day12.solveRow2(day12.unfold(row01))).isEqualTo(16384);
  }

  @Test
  void unfold() {
    assertThat(day12.unfold("???.### 1,1,3"))
        .isEqualTo("???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3");
  }

  @Test
  void solvePart02() {
    assertThat(day12.solvePart02()).isEqualTo(7);
  }
}
