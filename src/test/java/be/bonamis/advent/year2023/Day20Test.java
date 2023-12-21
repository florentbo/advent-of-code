package be.bonamis.advent.year2023;

import be.bonamis.advent.year2023.Day20.DestinationModule;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.tuple.Pair;
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
  void pushButton() {
    String sample =
        """
               broadcaster -> a, b, c
               %a -> b
               %b -> c
               %c -> inv
               &inv -> a
                              """;
    Day20 day20 = new Day20(sample);
    int lowPulses = 0;
    int highPulses = 0;
    day20.pushButton();

    assertThat(lowPulses).isEqualTo(8);
    assertThat(highPulses).isEqualTo(4);
  }
}
