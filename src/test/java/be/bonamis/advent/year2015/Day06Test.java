package be.bonamis.advent.year2015;

import static be.bonamis.advent.year2015.Day06.*;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Slf4j
class Day06Test {
  @Test
  void parse() {
    assertThat(parseLightInstruction("turn on 887,9 through 959,629"))
        .isEqualTo(new Instruction("turn on", new Limits(Point.of(887, 9), Point.of(959, 629))));

    assertThat(parseLightInstruction("turn on 0,0 through 999,999"))
        .isEqualTo(new Instruction("turn on", new Limits(Point.of(0, 0), Point.of(999, 999))));

    assertThat(parseLightInstruction("toggle 0,0 through 999,0"))
        .isEqualTo(new Instruction("toggle", new Limits(Point.of(0, 0), Point.of(999, 0))));

    assertThat(parseLightInstruction("turn off 499,499 through 500,500"))
        .isEqualTo(new Instruction("turn off", new Limits(Point.of(499, 499), Point.of(500, 500))));
  }

  @Test
  void instructionToLights() {
    Limits instruction = new Limits(Point.of(3, 1), Point.of(4, 1));
    assertThat(lights(instruction)).containsExactly(new Light(3, 1), new Light(4, 1));
  }

  @Test
  void turnOn_one_light() {
    Map<Light, Boolean> startingLights = init(Point.of(1, 1));

    Map<Light, Boolean> turnedOnLights =
        turnOn(startingLights, Limits.of(Point.of(1, 1), Point.of(1, 1)));

    assertThat(turnedOnLights).containsEntry(new Light(1, 1), true);

    assertThat(turnedOnLights).isEqualTo(startingLightWithOneTurnedOn());
  }

  private Map<Light, Boolean> startingLightWithOneTurnedOn() {
    return Map.of(
        new Light(0, 0), false,
        new Light(1, 0), false,
        new Light(0, 1), false,
        new Light(1, 1), true);
  }

  @Test
  void turnOf_one_light() {
    Map<Light, Boolean> startingLights = new HashMap<>(startingLightWithOneTurnedOn());

    Map<Light, Boolean> turnedOffLights =
        turnOf(startingLights, Limits.of(Point.of(1, 1), Point.of(1, 1)));

    assertThat(turnedOffLights)
        .isEqualTo(
            Map.of(
                new Light(0, 0), false,
                new Light(1, 0), false,
                new Light(0, 1), false,
                new Light(1, 1), false));
  }

  @Test
  void toggle_one_light() {
    Map<Light, Boolean> startingLights = new HashMap<>(startingLightWithOneTurnedOn());

    Map<Light, Boolean> turnedOnLights =
        toggle(startingLights, Limits.of(Point.of(1, 1), Point.of(1, 1)));

    assertThat(turnedOnLights)
        .isEqualTo(
            Map.of(
                new Light(0, 0), false,
                new Light(1, 0), false,
                new Light(0, 1), false,
                new Light(1, 1), false));
  }

  @Test
  void executeInstructions() {
    String input =
        """
                turn on 1,1 through 1,1
                turn off 1,1 through 1,1
                toggle 1,1 through 1,1
                """;
    List<String> instructions = Arrays.asList(input.split("\\n"));
    Map<Light, Boolean> lights = execute(instructions, Point.of(1, 1));
    assertThat(lights)
        .isEqualTo(
            Map.of(
                new Light(0, 0), false,
                new Light(1, 0), false,
                new Light(0, 1), false,
                new Light(1, 1), true));
  }

  @Test
  void lightsOnCount() {
    assertThat(countLightsOn(startingLightWithOneTurnedOn())).isEqualTo(1);
  }

  @Test
  void solvePart01() {
    String input =
        """
                    turn on 1,1 through 1,1
                    turn off 1,1 through 1,1
                    toggle 1,1 through 1,1
                    """;
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    assertThat(new Day06(inputStream).solve(Point.of(1, 1))).isEqualTo(1);
  }
}
