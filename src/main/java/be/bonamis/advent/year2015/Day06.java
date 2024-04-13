package be.bonamis.advent.year2015;

import static be.bonamis.advent.DayDataRetriever.dayUrl;
import static be.bonamis.advent.DayDataRetriever.downloadInput;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
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

  static Map<Light, Boolean> init(Point end) {
    Limits limits = new Limits(Point.of(0, 0), end);
    return lights(limits).collect(Collectors.toMap(l -> l, l -> false));
  }

  static Map<Light, Boolean> execute(List<String> instructions, Point end) {
    Map<Light, Boolean> startingLights = init(end);
    for (String instruction : instructions) {
      Instruction parsed = parseLightInstruction(instruction);
      switch (parsed.action) {
        case "turn on" -> turnOn(startingLights, parsed.limits());
        case "turn off" -> turnOf(startingLights, parsed.limits());
        case "toggle" -> toggle(startingLights, parsed.limits());
      }
    }
    return startingLights;
  }

  static long countLightsOn(Map<Light, Boolean> lights) {
    return lights.values().stream().filter(b -> b).count();
  }

  static Map<Light, Boolean> toggle(Map<Light, Boolean> lights, Limits limits) {
    Stream<Light> limitLights = lights(limits);
    limitLights.forEach(l -> lights.put(l, !lights.get(l)));

    return lights;
  }

  static Map<Light, Boolean> turnOf(Map<Light, Boolean> lights, Limits limits) {
    return switchLights(lights, limits, false);
  }

  static Map<Light, Boolean> turnOn(Map<Light, Boolean> lights, Limits limits) {
    return switchLights(lights, limits, true);
  }

  private static Map<Light, Boolean> switchLights(
      Map<Light, Boolean> lights, Limits limits, boolean state) {
    Stream<Light> limitLights = lights(limits);
    lights.putAll(limitLights.collect(Collectors.toMap(l -> l, l -> state)));
    return lights;
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
    return solve(Point.of(999, 999));
  }

  long solve(Point end) {
    Map<Light, Boolean> execute = execute(this.puzzle, end);
    return countLightsOn(execute);
  }

  @Override
  public long solvePart02() {
    return 777;
  }

  public static void main(String[] args) {
    String puzzleInputUrl = dayUrl(2015, 6) + "/input";
    InputStream inputStream = downloadInput(puzzleInputUrl);
    Day06 day05 = new Day06(inputStream);
    System.out.println("Day 06");
    System.out.println("Part 1: " + day05.solvePart01());
    System.out.println("Part 2: " + day05.solvePart02());
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
