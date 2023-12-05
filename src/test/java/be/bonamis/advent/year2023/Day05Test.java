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

    Long seed02 = seeds.list().get(1);

    assertThat(day05.correspond(lineMaps.get(0), seeds.list().get(2))).isEqualTo(57L);
    assertThat(day05.correspond(lineMaps.get(0), seeds.list().get(3))).isEqualTo(13L);

    Long seed01 = seeds.list().get(0);
    assertThat(day05.correspond(lineMaps.get(0), seed01)).isEqualTo(81L);
    assertThat(day05.correspond(lineMaps.get(1), 81L)).isEqualTo(81L); // 81 81 81 74 78 78 82
    assertThat(day05.correspond(lineMaps.get(2), 81L)).isEqualTo(81L); // 81 81 81 74 78 78 82
    assertThat(day05.correspond(lineMaps.get(3), 81L)).isEqualTo(74L); // 81 81 81 74 78 78 82
    assertThat(day05.correspond(lineMaps.get(4), 74L)).isEqualTo(78L); // 81 81 81 74 78 78 82
    assertThat(day05.correspond(lineMaps.get(5), 78L)).isEqualTo(78L); // 81 81 81 74 78 78 82
    assertThat(day05.correspond(lineMaps.get(6), 78L)).isEqualTo(82L); // 81 81 81 74 78 78 82

    assertThat(day05.correspond(lineMaps.get(0), seed02)).isEqualTo(14L);
    assertThat(day05.correspond(lineMaps.get(1), 14L)).isEqualTo(53L);

    assertThat(day05.location(lineMaps, seed01)).isEqualTo(82L);
    assertThat(day05.location(lineMaps, seed02)).isEqualTo(43L);
    assertThat(day05.location(lineMaps, seeds.list().get(2))).isEqualTo(86L);
    assertThat(day05.location(lineMaps, seeds.list().get(3))).isEqualTo(35L);

    assertThat(day05.solvePart01()).isEqualTo(35);
  }

  @Test
  void solvePart02() {
    assertThat(day05.solvePart02()).isEqualTo(7);
  }
}
