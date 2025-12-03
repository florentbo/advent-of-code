package be.bonamis.advent.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;

class Day03Test {

  String sampleInput =
"""
987654321111111
811111111111119
234234234234278
818181911112111
""";

  @Test
  void largest() {
    Day03 day03 = new Day03(sampleInput);
    Day03.Input.Bank first = day03.getInput().banks().get(0);
    List<Integer> batteries = first.batteries();
    int largest = batteries.stream().mapToInt(Integer::intValue).max().orElseThrow();
    List<Integer> largestIndexes =
        IntStream.range(0, batteries.size())
            .filter(i -> batteries.get(i) == largest)
            .boxed()
            .toList();

    List<Integer> list =
        largestIndexes.stream()
            .map(
                i -> {
                  List<Integer> integers = batteries.subList(i + 1, batteries.size());
                  return integers.stream().mapToInt(Integer::intValue).max().orElseThrow();
                })
            .toList();
    int max = list.stream().mapToInt(Integer::intValue).max().orElseThrow();
    int s = Integer.parseInt(largest + "" + max);
    assertThat(largest).isEqualTo(9);
    assertThat(largestIndexes).containsExactly(0);
    assertThat(s).isEqualTo(98);
    assertThat(first.largestJoltage()).isEqualTo(98);
  }

  @Test
  void solvePart01() {
    Day03 day03 = new Day03(sampleInput);
    long solvePart01 = day03.solvePart01();
    assertThat(solvePart01).isEqualTo(357L);
  }
}
