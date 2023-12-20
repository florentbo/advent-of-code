package be.bonamis.advent.year2023;

import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day20Test {

  private Day20 day20;

  @BeforeEach
  void setUp() {
    String sample =
        """
       broadcaster -> a, b, c
       %a -> b
       %b -> c
       %c -> inv
       &inv -> a
                      """;
    day20 = new Day20(Arrays.asList(sample.split("\n")));
  }

  @Test
  void parse() {
    List<String> input = List.of("broadcaster -> a, b, c");
    Map<String, List<String>> map =
        input.stream()
            .map(pair -> pair.split("->"))
            .collect(Collectors.toMap(pair -> pair[0].strip(), pair -> toList(pair[1].strip())));
    log.debug("map: {}", map);
    assertThat(map).hasSize(1);
  }

  private List<String> toList(String destinations) {
    return Arrays.stream(destinations.split(",")).map(String::strip).toList();
  }
}
