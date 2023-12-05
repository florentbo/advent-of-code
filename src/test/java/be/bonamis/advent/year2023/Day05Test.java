package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.ArrayList;

import be.bonamis.advent.year2023.Day05.LineOfMap;
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
    Day05.Seeds seeds = day05.getSeeds();
    List<LineOfMap> lineOfMaps01 = day05.lineOfMap(2, 6);
    List<LineOfMap> lineOfMaps02 = day05.lineOfMap(6, 11);
    List<LineOfMap> lineOfMaps03 = day05.lineOfMap(11, 11);
    List<LineOfMap> lineOfMaps04 = day05.lineOfMap(17, 11);
    List<LineOfMap> lineOfMaps05 = day05.lineOfMap(21, 11);
    List<LineOfMap> lineOfMaps06 = day05.lineOfMap(26, 11);
    List<LineOfMap> lineOfMaps07 = day05.lineOfMap(30, 11);

    assertThat(corresp(lineOfMaps01, seeds.list().get(0))).isEqualTo(81L);
    assertThat(corresp(lineOfMaps01, seeds.list().get(1))).isEqualTo(14L);
    assertThat(corresp(lineOfMaps01, seeds.list().get(2))).isEqualTo(57L);
    assertThat(corresp(lineOfMaps01, seeds.list().get(3))).isEqualTo(13L);

    assertThat(corresp(lineOfMaps02, 81L)).isEqualTo(81L);
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
