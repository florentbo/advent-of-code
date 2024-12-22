package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.*;

@Slf4j
@Getter
public class Day13 extends TextDaySolver {
  private final Inputs inputs;

  public Day13(InputStream inputStream) {
    super(inputStream);
    this.inputs = Inputs.of(this.puzzle);
  }

  public Day13(String input) {
    super(input);
    this.inputs = Inputs.of(this.puzzle);
  }

  @Override
  public long solvePart01() {
    return this.inputs.inputs().stream()
        .map(Input::intersectionPoint)
        .map(point -> point.x() * 3 + point.y())
        .reduce(0L, Long::sum);
  }

  @Override
  public long solvePart02() {
    return 0;
  }

  public record Inputs(List<Input> inputs) {
    public static Inputs of(List<String> puzzle) {
      List<Input> list =
          IntStream.range(0, puzzle.size() / 4)
              .mapToObj(
                  i -> Input.of(puzzle.get(i * 4), puzzle.get(i * 4 + 1), puzzle.get(i * 4 + 2)))
              .toList();
      return new Inputs(list);
    }
  }

  record Input(Button buttonA, Button buttonB, Prize prize) {
    public static Input of(Button buttonA, Button buttonB, Prize prize) {
      return new Input(buttonA, buttonB, prize);
    }

    public static Input of(String s, String s1, String s2) {
      return Input.of(Button.of(s), Button.of(s1), Prize.of(s2));
    }

    LineEquation.Point intersectionPoint() {
      LineEquation line1 = LineEquation.of(buttonA.x(), buttonB().x(), -prize.x());
      LineEquation line2 = LineEquation.of(buttonA.y(), buttonB().y(), -prize.y());
      LineEquation.Point point = line1.intersectionPoint(line2);
      LineEquation.Point point1 =
          point.x() > 100 || point.y() > 100 || point.x() < 0 || point.y() < 0
              ? LineEquation.Point.of(0, 0)
              : point;
      log.info("point {} and point1 {}", point, point1);
      return point1;
    }
  }

  record LineEquation(double x, double y, double c) {
    public static LineEquation of(double x, double y, double c) {
      return new LineEquation(x, y, c);
    }

    // ax + by + c = 0
    // y = -(ax + c) / b

    Point intersectionPoint(LineEquation line2) {
      log.debug("line1: x={}, y={}, c={}", x, y, c);
      log.debug("line2: x={}, y={}, c={}", line2.x(), line2.y(), line2.c());

      RealMatrix coefficients =
          new Array2DRowRealMatrix(new double[][] {{x, y}, {line2.x(), line2.y()}}, false);
      DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

      RealVector constants = new ArrayRealVector(new double[] { -c, -line2.c() });
      RealVector solution = solver.solve(constants);
      log.info("solution: {}", solution);

      double m1 = -x / y;
      double b1 = -c / y;
      double m2 = -line2.x / line2.y;
      double b2 = -line2.c / line2.y;
      return calculateIntersectionPoint(m1, b1, m2, b2).orElse(Point.of(0, 0));
    }

    record Point(long x, long y) {
      static Point of(long x, long y) {
        return new Point(x, y);
      }
    }

    // y = m1x + b1
    // y = m2x + b2
    Optional<Point> calculateIntersectionPoint(double m1, double b1, double m2, double b2) {

      /* RealMatrix coefficients =
              new Array2DRowRealMatrix(new double[][] { { 2, 3, -2 }, { -1, 7, 6 }, { 4, -3, -5 } },
                      false);
      DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();*/

      if (m1 == m2) {
        return Optional.empty();
      }

      double x = (b2 - b1) / (m1 - m2);
      double y = m1 * x + b1;

      return Optional.of(Point.of(Math.round(x), Math.round(y)));
    }
  }

  public record Prize(long x, long y) {
    public static Prize of(String input) {
      Pattern pattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");
      Matcher matcher = pattern.matcher(input);
      if (matcher.find()) {
        long x = Long.parseLong(matcher.group(1));
        long y = Long.parseLong(matcher.group(2));
        return new Prize(x, y);
      }
      return null;
    }
  }

  record Button(long x, long y) {
    public static Button of(String input) {
      Pattern pattern = Pattern.compile("Button ([AB]): X\\+(\\d+), Y\\+(\\d+)");
      Matcher matcher = pattern.matcher(input);
      log.debug("input={}", input);
      log.debug("matcher={}", matcher);
      if (matcher.find()) {
        String match = matcher.group(1);
        long x = Long.parseLong(matcher.group(2));
        long y = Long.parseLong(matcher.group(3));
        log.debug("match={}, x={}, y={}", match, x, y);
        return new Button(x, y);
      }

      return null;
    }
  }
}
