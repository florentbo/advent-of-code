package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day15Test {
  String text = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
  private final Day15 day15 = new Day15(text);

  @Test
  void solvePart01() {
    assertThat(day15.solvePart01()).isEqualTo(1320);
  }

  @Test
  void running() {
    int start = 0;
    assertThat((int) 'H').isEqualTo(72);

    assertThat(day15.modify(start, 'H')).isEqualTo(200);
    assertThat(day15.modify(200, 'A')).isEqualTo(153);
    assertThat(day15.modify(153, 'S')).isEqualTo(172);
    assertThat(day15.modify(172, 'H')).isEqualTo(52);

    assertThat(day15.running("HASH")).isEqualTo(52);
    assertThat(day15.running("rn=1")).isEqualTo(30);
  }
}
