package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.utils.FileHelper;
import java.util.*;

import be.bonamis.advent.year2023.Day05.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
class Day05Test {

  private Day05 day05;

  @BeforeEach
  void setUp() {
    String content = FileHelper.content("2023/05/2023_05_02_code.txt");
    day05 = new Day05(Arrays.asList(content.split("\n")));
  }

  @Test
  void solvePart01() {
    Seeds seeds = day05.getSeeds();
    List<List<LineOfMap>> lineMaps = day05.getLineMaps();
    assertThat(lineMaps).hasSize(7);

    assertThat(corresp(lineMaps.get(0), seeds.list().get(1))).isEqualTo(14L);
    assertThat(corresp(lineMaps.get(0), seeds.list().get(2))).isEqualTo(57L);
    assertThat(corresp(lineMaps.get(0), seeds.list().get(3))).isEqualTo(13L);

    assertThat(corresp(lineMaps.get(0), seeds.list().get(0))).isEqualTo(81L);
    assertThat(corresp(lineMaps.get(1), 81L)).isEqualTo(81L);//81 81 81 74 78 78 82
    assertThat(corresp(lineMaps.get(2), 81L)).isEqualTo(81L);//81 81 81 74 78 78 82
    assertThat(corresp(lineMaps.get(3), 81L)).isEqualTo(74L);//81 81 81 74 78 78 82
    assertThat(corresp(lineMaps.get(4), 74L)).isEqualTo(78L);//81 81 81 74 78 78 82
    assertThat(corresp(lineMaps.get(5), 78L)).isEqualTo(78L);//81 81 81 74 78 78 82
    assertThat(corresp(lineMaps.get(6), 78L)).isEqualTo(82L);//81 81 81 74 78 78 82
  }

  private Long corresp(List<LineOfMap> lines, Long l) {
    return lines.stream()
        .filter(
            line -> {
              long start = line.destination();
              long end = line.destination() + line.range();
              return l >= start && l <= end;
            })
        .findFirst()
        .map(line -> l + line.destination() - line.source())
        .orElse(l);
  }

  @Test
  void solvePart02() {
    assertThat(day05.solvePart02()).isEqualTo(7);
  }
}
