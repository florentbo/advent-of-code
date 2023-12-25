package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.year2023.Day19.Rating;
import be.bonamis.advent.year2023.Day19.WorkFlow;
import be.bonamis.advent.year2023.Day19.WorkFlow.Rule;
import java.util.*;
import java.util.List;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
class Day25Test {

  private Day25 day;

  @BeforeEach
  void setUp() {
    String sample =
        """
                      jqt: rhn xhk nvd
                      rsh: frs pzl lsr
                      xhk: hfx
                      cmg: qnr nvd lhk bvb
                      rhn: xhk bvb hfx
                      bvb: xhk hfx
                      pzl: lsr hfx nvd
                      qnr: nvd
                      ntq: jqt hfx bvb xhk
                      nvd: lhk
                      lsr: lhk
                      rzs: qnr cmg lsr rsh
                      frs: qnr lhk lsr




                      """;
    day = new Day25(Arrays.asList(sample.split("\n")));
  }

  /*@Test
  void parseRating() {
    String input = "jqt: rhn xhk nvd";
    Rating rating = Rating.parse(input);
    assertThat(rating.values()).isEqualTo(Map.of("x", 787L, "m", 2655L, "a", 1222L, "s", 2876L));
  }*/

  @Test
  void solvePart01() {
    assertThat(day.solvePart01()).isEqualTo(54);
  }
}
