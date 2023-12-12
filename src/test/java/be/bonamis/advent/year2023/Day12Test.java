package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.List;

import be.bonamis.advent.utils.FileHelper;
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
    assertThat(day12.solveRow2("???.### 1,1,3")).isEqualTo(1);
    String row00 = input.get(0);
    String row01 = input.get(1);

    assertThat(day12.solveRow2(row00)).isEqualTo(1);
    assertThat(day12.solveRow2(row01)).isEqualTo(4);
    assertThat(day12.solveRow2(input.get(2))).isEqualTo(1);
    assertThat(day12.solveRow2(input.get(3))).isEqualTo(1);
    assertThat(day12.solveRow2(input.get(4))).isEqualTo(4);
    assertThat(day12.solveRow2(input.get(5))).isEqualTo(10);

  }

  @Test
  void solvePart02Row() {
    assertThat(day12.solvePart02Row(input.get(0))).isEqualTo(1);
    assertThat(day12.solvePart02Row(input.get(1))).isEqualTo(16384);
    assertThat(day12.solvePart02Row(input.get(2))).isEqualTo(1);
  }

  @Test
  void unfold() {
    assertThat(day12.unfold("???.### 1,1,3"))
        .isEqualTo("???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3");
  }

  @Test
  void runnable() {
    String content = FileHelper.content("2023/12/2023_12_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day12 day = new Day12(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 1: {}", day.solvePart02());

  }

  @Test
  //@Disabled("need to find something more efficient")
  void solvePart02() {
    assertThat(day12.solvePart02()).isEqualTo(525152);
  }
}
