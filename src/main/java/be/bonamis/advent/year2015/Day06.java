package be.bonamis.advent.year2015;


import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.regex.*;
import java.util.stream.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day06 extends TextDaySolver {

  public Day06(InputStream sample) {
    super(sample);
  }

  static Stream<Light> lights(Limits limits) {
    return IntStream.rangeClosed(limits.start().x(), limits.end().x())
        .boxed()
        .flatMap(
            x ->
                IntStream.rangeClosed(limits.start().y(), limits.end().y())
                    .mapToObj(y -> Light.of(x, y)));
  }

  static Map<Light, Integer> init(Point end) {
    Limits limits = new Limits(Point.of(0, 0), end);
    return lights(limits).collect(Collectors.toMap(l -> l, l -> 0));
  }

  static Map<Light, Integer> execute(List<String> instructions, Point end, LightAction action) {
    Map<Light, Integer> startingLights = init(end);
    for (String instruction : instructions) {
      Instruction parsed = parseLightInstruction(instruction);
      switch (parsed.action) {
        case "turn on" -> turnOn(action, startingLights, parsed.limits());
        case "turn off" -> turnOf(action, startingLights, parsed.limits());
        case "toggle" -> toggle(action, startingLights, parsed.limits());
      }
    }
    return startingLights;
  }

  static Map<Light, Integer> turnOn(
      LightAction action, Map<Light, Integer> startingLights, Limits limits) {
    return switchLights(startingLights, limits, action.turnOn());
  }

  static Map<Light, Integer> turnOf(
      LightAction action, Map<Light, Integer> startingLights, Limits limits) {
    return switchLights(startingLights, limits, action.turnOff());
  }

  static Map<Light, Integer> toggle(
      LightAction action, Map<Light, Integer> startingLights, Limits limits) {
    return switchLights(startingLights, limits, action.toggle());
  }

  private static Map<Light, Integer> switchLights(
      Map<Light, Integer> lights, Limits limits, Function<Integer, Integer> turn) {
    lights(limits).forEach(l -> lights.put(l, turn.apply(lights.get(l))));
    return lights;
  }

  static long countLightsOn(Map<Light, Integer> lights) {
    return lights.values().stream().reduce(0, Integer::sum);
  }

  interface LightAction {
    Function<Integer, Integer> turnOn();

    Function<Integer, Integer> turnOff();

    Function<Integer, Integer> toggle();
  }

  static class FirstPart implements LightAction {
    public Function<Integer, Integer> turnOn() {
      return i -> 1;
    }

    public Function<Integer, Integer> turnOff() {
      return i -> 0;
    }

    public Function<Integer, Integer> toggle() {
      return i -> i == 1 ? 0 : 1;
    }
  }

  static class SecondPart implements LightAction {
    public Function<Integer, Integer> turnOn() {
      return i -> i + 1;
    }

    public Function<Integer, Integer> turnOff() {
      return i -> i > 0 ? i - 1 : 0;
    }

    public Function<Integer, Integer> toggle() {
      return i -> i + 2;
    }
  }

  static LightAction firstPart() {
    return new FirstPart();
  }

  static LightAction secondPart() {
    return new SecondPart();
  }

  static Instruction parseLightInstruction(String input) {
    Pattern pattern =
        Pattern.compile("(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)");
    Matcher matcher = pattern.matcher(input);

    if (matcher.matches()) {
      String instruction = matcher.group(1);
      int x1 = Integer.parseInt(matcher.group(2));
      int y1 = Integer.parseInt(matcher.group(3));
      int x2 = Integer.parseInt(matcher.group(4));
      int y2 = Integer.parseInt(matcher.group(5));
      return new Instruction(instruction, new Limits(Point.of(x1, y1), Point.of(x2, y2)));
    } else {
      throw new IllegalArgumentException("Invalid input: " + input);
    }
  }

  @Override
  public long solvePart01() {
    return solve(Point.of(999, 999), firstPart());
  }

  long solve(Point end, LightAction action) {
    Map<Light, Integer> execute = execute(this.puzzle, end, action);
    return countLightsOn(execute);
  }

  @Override
  public long solvePart02() {
    return solve(Point.of(999, 999), secondPart());
  }

  record Instruction(String action, Limits limits) {}

  record Light(int x, int y) {
    static Light of(int x, int y) {
      return new Light(x, y);
    }
  }

  record Point(int x, int y) {
    static Point of(int x, int y) {
      return new Point(x, y);
    }
  }

  record Limits(Point start, Point end) {
    static Limits of(Point start, Point end) {
      return new Limits(start, end);
    }
  }
}
