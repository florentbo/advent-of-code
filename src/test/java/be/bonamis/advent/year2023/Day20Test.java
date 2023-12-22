package be.bonamis.advent.year2023;

import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static be.bonamis.advent.year2023.Day20.DestinationModule.State.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
class Day20Test {

  @Test
  void parse() {
    List<String> input = List.of("broadcaster -> a, b, c");

    Day20 day20 = new Day20(input);

    assertThat(day20.getModuleConfiguration())
        .hasSize(2)
        .isEqualTo(Map.of("broadcaster", List.of("a", "b", "c"), "button", List.of("broadcaster")));
    assertThat(day20.getStates()).hasSize(2).isEqualTo(Map.of("broadcaster", OFF, "button", OFF));
  }

  @Test
  void filterModules() {
    String sample =
        """
           broadcaster -> a, b, c
           %a -> b
           %b -> c
           %c -> inv
           &inv -> a
                          """;
    Day20 day20 = new Day20(sample);

    assertThat(day20.fliFlops())
        .hasSize(3)
        .containsExactly(
            entry("a", List.of("b")), entry("b", List.of("c")), entry("c", List.of("inv")));

    assertThat(day20.conjunctions()).hasSize(1).containsExactly(entry("inv", List.of("a")));

    assertThat(day20.getStates())
        .hasSize(6)
        .contains(
            entry("button", OFF),
            entry("broadcaster", OFF),
            entry("a", OFF),
            entry("b", OFF),
            entry("c", OFF),
            entry("inv", OFF));
  }

  @Test
  void pushButtonFirstSample() {
    String sample =
        """
               broadcaster -> a, b, c
               %a -> b
               %b -> c
               %c -> inv
               &inv -> a
                              """;
    Day20 day20 = new Day20(sample);
    Day20.Counts counts = day20.pushButton(1L);

    assertThat(counts).isEqualTo(new Day20.Counts(8L, 4L));
  }

  @Test
  void pushButtonFirstSampleMultipleTimes() {
    String sample =
        """
               broadcaster -> a, b, c
               %a -> b
               %b -> c
               %c -> inv
               &inv -> a
                              """;
    Day20 day20 = new Day20(sample);
    Day20.Counts counts = day20.pushButton(1000L);

    assertThat(counts).isEqualTo(new Day20.Counts(8000L, 4000L));
  }

  @Test
  void solvePart01FirstSample() {
    String sample =
        """
                   broadcaster -> a, b, c
                   %a -> b
                   %b -> c
                   %c -> inv
                   &inv -> a
                                  """;
    Day20 day20 = new Day20(sample);
    assertThat(day20.solvePart01()).isEqualTo(32000000L);
  }

  @Test
  void pushButtonSecondSample() {
    String sample =
        """
           broadcaster -> a
           %a -> inv, con
           &inv -> b
           %b -> con
           &con -> output
                              """;
    Day20 day20 = new Day20(sample);
    Day20.Counts counts = day20.pushButton(1L);
    assertThat(counts).isEqualTo(new Day20.Counts(4L, 4L));
  }

  @Test
  void pushButtonSecondSampleTwoPushes() {
    String sample =
        """
           broadcaster -> a
           %a -> inv, con
           &inv -> b
           %b -> con
           &con -> output
                              """;
    Day20 day20 = new Day20(sample);
    Day20.Counts counts = day20.pushButton(2L);
    assertThat(counts).isEqualTo(new Day20.Counts(8L, 6L));
  }
}
