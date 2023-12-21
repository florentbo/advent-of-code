package be.bonamis.advent.year2023;

import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@Slf4j
class Day20Test {

  @Test
  void parse() {
    List<String> input = List.of("broadcaster -> a, b, c");
    Map<String, List<String>> map = new Day20(input).getModuleConfiguration();
    log.debug("map: {}", map);
    assertThat(map).hasSize(1).isEqualTo(Map.of("broadcaster", List.of("a", "b", "c")));
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
  }
}
