package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.TreeSet;

import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day17Test {

  @Test
  void solvePart01() {
    String content = FileHelper.content("2023/17/2023_17_00_code.txt");
    Day17 day17 = new Day17(Arrays.asList(content.split("\n")));
    assertThat(day17.solvePart01()).isEqualTo(102);
  }

  @Test
  void solvePart01Test() {
    String content = FileHelper.content("2023/17/2023_17_00_sample.txt");
    Day17 day17 = new Day17(Arrays.asList(content.split("\n")));
    assertThat(day17.solvePart01()).isEqualTo(21 - 1);
  }

  @Test
  void treeSetMin() {
    Set<Integer> set = new TreeSet<>();
    set.add(3);
    set.add(1);
    set.add(2);
    Integer first = set.iterator().next();
    assertThat(first).isEqualTo(1);
  }
}
