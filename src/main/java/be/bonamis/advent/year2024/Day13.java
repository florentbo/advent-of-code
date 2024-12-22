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

import static be.bonamis.advent.year2024.Day13.LineEquation.*;

@Slf4j
@Getter
public class Day13 extends TextDaySolver {
  private static final long PART2_OFFSET = 10_000_000_000_000L;
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
    return solve(0, true);
  }

  private Long solve(long offset, boolean applyHundredLimitation) {
    return this.inputs.inputs().stream()
        .map(input -> risePrize(input, offset))
        .map(input1 -> input1.intersectionPoint(applyHundredLimitation))
        .map(point -> point.x() * 3 + point.y())
        .reduce(0L, Long::sum);
  }

  private Input risePrize(Input input, long offset) {
    Prize prize = input.prize();
    Prize newPrize = Prize.of(prize.x() + offset, prize.y() + offset);
    return Input.of(input.buttonA(), input.buttonB(), newPrize);
  }

  @Override
  public long solvePart02() {
    return solve(PART2_OFFSET, false);
  }

  public record Inputs(List<Input> inputs) {
    public static Inputs of(List<String> puzzle) {
      List<Input> list =
          IntStream.range(0, (puzzle.size() + 1) / 4)
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

    Point intersectionPoint(boolean applyHundredLimitation) {
      LineEquation line1 = LineEquation.of(buttonA.x(), buttonB().x(), -prize.x());
      LineEquation line2 = LineEquation.of(buttonA.y(), buttonB().y(), -prize.y());
      Point point = line1.intersectionPoint(line2);
      boolean limitValidation = !applyHundredLimitation || point.x() < 100 || point.y() < 100;
      log.debug("limitValidation={} and point={}", limitValidation, point);
      boolean validation = limitValidation && isIntersectionValid(point);
      log.debug("validation={} and point={}", validation, point);
      Point point1 = validation ? point : Point.of(0, 0);
      log.debug("point {} and point1 {}", point, point1);
      return point1;
    }

    boolean isIntersectionValid(Point point) {
      boolean xValidation = (point.x() * buttonA.x() + point.y() * buttonB.x()) == prize.x();
      boolean yValidation = (point.x() * buttonA.y() + point.y() * buttonB.y()) == prize.y();
      return xValidation && yValidation;
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

      RealVector constants = new ArrayRealVector(new double[] {-c, -line2.c()});
      RealVector solution = solver.solve(constants);
      log.debug("solution: {}", solution);

      return Point.of(Math.round(solution.getEntry(0)), Math.round(solution.getEntry(1)));
    }

    record Point(long x, long y) {
      static Point of(long x, long y) {
        return new Point(x, y);
      }
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

    static Prize of(long x, long y) {
      return new Prize(x, y);
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
