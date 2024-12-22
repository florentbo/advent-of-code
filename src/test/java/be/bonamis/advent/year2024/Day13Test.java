package be.bonamis.advent.year2024;

import static be.bonamis.advent.year2024.Day13.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.year2024.Day13.LineEquation.Point;
import org.junit.jupiter.api.*;

class Day13Test {

  public static final String INPUT =
      """
          Button A: X+94, Y+34
          Button B: X+22, Y+67
          Prize: X=8400, Y=5400

          Button A: X+26, Y+66
          Button B: X+67, Y+21
          Prize: X=12748, Y=12176

          Button A: X+17, Y+86
          Button B: X+84, Y+37
          Prize: X=7870, Y=6450

          Button A: X+69, Y+23
          Button B: X+27, Y+71
          Prize: X=18641, Y=10279
          """;

  @Test
  void solvePart01() {
    assertThat(new Day13(INPUT).solvePart01()).isEqualTo(480L);
  }

  @Test
  void parseButton() {
    final String input = "Button A: X+94, Y+34";
    Button button = Button.of(input);
    assertThat(button).isNotNull();
    assertThat(button.x()).isEqualTo(94L);
    assertThat(button.y()).isEqualTo(34L);
  }

  @Test
  void parsePrize() {
    final String input = "Prize: X=8400, Y=5400";
    Prize prize = Prize.of(input);
    assertThat(prize).isNotNull();
    assertThat(prize.x()).isEqualTo(8400L);
    assertThat(prize.y()).isEqualTo(5400L);
  }

  @Test
  void twoLinesIntersect() {
    /*
    y = 3x-3
    y = 2.3x+4
    intersection point is (10, 27)
     */
    LineEquation line1 = LineEquation.of(3, -1, -3);
    LineEquation line2 = LineEquation.of(2.3, -1, 4);
    Point intersectionPoint = line1.intersectionPoint(line2);
    assertThat(intersectionPoint).isEqualTo(Point.of(10, 27));
  }

  @Test
  void twoLinesOfSampleIntersect() {
    LineEquation line1 = LineEquation.of(94, 22, -8400);
    LineEquation line2 = LineEquation.of(34, 67, -5400);
    Point intersectionPoint = line1.intersectionPoint(line2);
    assertThat(intersectionPoint).isEqualTo(Point.of(80, 40));
  }

  @Test
  void inputIntersectionPoint() {
    Input input = new Day13(INPUT).getInputs().inputs().get(0);
    assertThat(input.intersectionPoint()).isEqualTo(Point.of(80, 40));
  }

  @Test
  void inputHaNonIntersectionPoint() {
    Input input = new Day13(INPUT).getInputs().inputs().get(1);
    assertThat(input.intersectionPoint()).isEqualTo(Point.of(0, 0));
  }
}
