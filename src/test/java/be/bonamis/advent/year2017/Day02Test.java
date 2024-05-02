package be.bonamis.advent.year2017;

import static be.bonamis.advent.utils.FileHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import be.bonamis.advent.year2017.Day02.Row;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

@Slf4j
class Day02Test {

  @Test
  void parser() {
    assertThat(Row.of("5  1    9 5")).isEqualTo(firsRow());
  }

  private Row firsRow() {
    return new Row(List.of(5, 1, 9, 5));
  }

  @Test
  void min() {
    assertThat(firsRow().min()).isEqualTo(1);
  }

  @Test
  void max() {
    assertThat(firsRow().max()).isEqualTo(9);
  }

  @Test
  void difference() {
    assertThat(firsRow().difference()).isEqualTo(8);
  }

  @Test
  void solvePart01() {
    assertThat(
            new Day02(
                    inputStream(
                        """
        5 1 9 5
        7 5 3
        2 4 6 8
        """))
                .solvePart01())
        .isEqualTo(18);
  }

  @Test
  void combinationsList() {
    assertThat(Row.combinations(secondInput()))
        .containsExactly(
            List.of(5, 9),
            List.of(5, 2),
            List.of(5, 8),
            List.of(9, 2),
            List.of(9, 8),
            List.of(2, 8));
  }

  private List<Integer> secondInput() {
    return List.of(5, 9, 2, 8);
  }

  @Test
  void combinations() {
    assertThat(secondInputRow().combinations())
        .containsExactly(
            Pair.of(5, 9),
            Pair.of(5, 2),
            Pair.of(5, 8),
            Pair.of(9, 2),
            Pair.of(9, 8),
            Pair.of(2, 8));
  }

  private Row secondInputRow() {
    return new Row(secondInput());
  }

  @Test
  void evenlyDivisibleValues() {
    assertThat(secondInputRow().evenlyDivisibleValues()).isEqualTo(Pair.of(2, 8));
  }

  @Test
  void evenlyDivisibleValuesDifference() {
    assertThat(secondInputRow().evenlyDivisibleValuesDifference()).isEqualTo(4);
  }

  @Test
  void solvePart02() {
    assertThat(new Day02(inputStream(text2)).solvePart02()).isEqualTo(9);
  }

  static String text2 =
      """
          5 9 2 8
          9 4 7 3
          3 8 6 5
          """;
}
