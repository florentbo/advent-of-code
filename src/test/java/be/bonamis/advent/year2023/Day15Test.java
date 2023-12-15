package be.bonamis.advent.year2023;

import static be.bonamis.advent.year2023.Day15.hash;
import static be.bonamis.advent.year2023.Day15.modify;
import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.year2023.Day15.Lens;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

import java.util.*;

@Slf4j
class Day15Test {
  public static final Lens LENS_02 = new Lens("cm", '-', 0);
  public static final Lens LENS_03 = new Lens("qhbrv", '=', 3);
  String text = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
  private final Day15 day15 = new Day15(text);

  @Test
  void solvePart01() {
    assertThat(day15.solvePart01()).isEqualTo(1320);
  }

  @Test
  void solvePart02() {
    assertThat(day15.solvePart02()).isEqualTo(145);
  }

  @Test
  void running() {
    int start = 0;
    assertThat((int) 'H').isEqualTo(72);

    assertThat(modify(start, 'H')).isEqualTo(200);
    assertThat(modify(200, 'A')).isEqualTo(153);
    assertThat(modify(153, 'S')).isEqualTo(172);
    assertThat(modify(172, 'H')).isEqualTo(52);

    assertThat(hash("HASH")).isEqualTo(52);
    assertThat(hash("rn=1")).isEqualTo(30);
  }

  @Test
  void parser() {
    Lens lens01 = Lens.of("rn=1");
    Lens lens02 = Lens.of("cm-");
    Lens lens03 = Lens.of("qhbrv=3");

    assertThat(lens01).isEqualTo(LENS_01);
    assertThat(lens02).isEqualTo(LENS_02);
    assertThat(lens03).isEqualTo(LENS_03);
  }

  public static final Lens LENS_01 = new Lens("rn", '=', 1);

  @Test
  void addToBoxes() {
    log.info("{}", LENS_01.hash());
    log.info("{}", LENS_02.hash());
    log.info("{}", LENS_03.hash());
    // List<String> lenses = day15.getLenses().toList();
    /*
        After "rn=1":
    Box 0: [rn 1]

    After "cm-":
    Box 0: [rn 1]

    After "qp=3":
    Box 0: [rn 1]
    Box 1: [qp 3]

    After "cm=2":
    Box 0: [rn 1] [cm 2]
    Box 1: [qp 3]

    After "qp-":
    Box 0: [rn 1] [cm 2]
         */

    Map<Long, Map<String, Long>> boxes = new Day15(text).boxes(1);
    log.debug("boxes: {}", boxes);
    assertThat(boxes.keySet()).hasSize(1);

    boxTest(2, 1);
    boxTest(3, 2);
    boxTest(4, 2);
    boxTest(5, 1);
    boxTest(6, 2);
    boxTest(7, 2);
    boxTest(8, 2);
    boxTest(9, 2);
    boxTest(10, 2);
    boxTest(11, 2);

    /*
        After "pc=4":
    Box 0: [rn 1] [cm 2]
    Box 3: [pc 4]

    After "ot=9":
    Box 0: [rn 1] [cm 2]
    Box 3: [pc 4] [ot 9]

    After "ab=5":
    Box 0: [rn 1] [cm 2]
    Box 3: [pc 4] [ot 9] [ab 5]
         */
  }

  private void boxTest(int i, int expected) {
    Map<Long, Map<String, Long>> boxes = new Day15(text).boxes(i);
    log.debug("boxes: {}", boxes);
    assertThat(boxes.keySet()).hasSize(expected);
  }
}
