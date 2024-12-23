package be.bonamis.advent.year2024;

import static be.bonamis.advent.year2024.Day23.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

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
    assertThat(input.interConnectedComputers()).hasSize(20);
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
        yn-cg
        """;

    Day23 day23 = new Day23(sample);
    Input input = day23.getInput();
    assertThat(input.computerPairs()).hasSize(7);
    assertThat(input.interConnectedComputers()).hasSize(2);
  }
}
