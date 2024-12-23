package be.bonamis.advent.year2024;

import static be.bonamis.advent.year2024.Day23.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Day23Test {
  public static final String INPUT =
      """
                kh-tc
                qp-kh
                de-cg
                ka-co
                yn-aq
                qp-ub
                cg-tb
                vc-aq
                tb-ka
                wh-tc
                yn-cg
                kh-ub
                ta-co
                de-co
                tc-td
                tb-wq
                wh-td
                ta-ka
                td-qp
                aq-cg
                wq-ub
                ub-vc
                de-ta
                wq-aq
                wq-vc
                wh-yn
                ka-de
                kh-ta
                co-tc
                wh-qp
                tb-vc
                td-yn
                """;

  @Test
  void solvePart01() {
    assertThat(new Day23(INPUT).solvePart01()).isEqualTo(7);
  }

  @Test
  void interConnectedComputersTest() {
    Day23 day23 = new Day23(INPUT);
    Input input = day23.getInput();
    assertThat(input.computerPairs()).hasSize(32);

    String expected =
        """
                aq,cg,yn
                aq,vc,wq
                co,de,ka
                co,de,ta
                co,ka,ta
                de,ka,ta
                kh,qp,ub
                qp,td,wh
                tb,vc,wq
                tc,td,wh
                td,wh,yn
                ub,vc,wq
                """;

    Set<Set<String>> expectedSet =
        expected.lines().map(s -> Set.of(s.split(","))).collect(Collectors.toSet());

    List<String> list =
        expectedSet.stream()
            .map(s -> List.of(s.stream().sorted().toList()).toString())
            .sorted()
            .toList();

    Set<Set<String>> actual = input.interConnectedComputers();
    List<String> actualList =
        actual.stream()
            .map(
                s -> {
                  return List.of(s.stream().sorted().toList()).toString();
                })
            .sorted()
            .toList();
    assertThat(actualList).isEqualTo(list);

    // missing  "[[co, de, ka]]",
  }

  @Test
  void interConnectedComputersSmallSampleTest() {
    String sample =
        """
        yn-aq
        qp-ub
        cg-tb
        vc-aq
        tb-ka
        wh-tc
        aq-cg
        yn-cg
        """;

    Day23 day23 = new Day23(sample);
    Input input = day23.getInput();
    assertThat(input.computerPairs()).hasSize(8);
    assertThat(input.interConnectedComputers()).hasSize(1);
  }
}
