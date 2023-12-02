package be.bonamis.advent.year2015;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Day02Test {

  @Test
  void solvePart01() {
    String input = "2x3x4";
    String[] dimensions = input.split("x");
    int l = Integer.parseInt(dimensions[0]);
    int w = Integer.parseInt(dimensions[1]);
    int h = Integer.parseInt(dimensions[2]);
    Day02 day02 = new Day02(List.of(input));
    // 2*l*w + 2*w*h + 2*h*l
    // extends DaySolver<String> { 52+6
    assertThat(day02.boxArea(l, w, h)).isEqualTo(52);
    assertThat(day02.min(l * w, w * h, h * l)).isEqualTo(6);
    assertThat(day02.paper(l, w, h)).isEqualTo(58);
    assertThat(day02.paper(input)).isEqualTo(58);
    assertThat(day02.paper("1x1x10")).isEqualTo(43);
    assertThat(new Day02(List.of(input)).solvePart01()).isEqualTo(58);
  }

  @Test
  void solvePart02() {
    String input = "2x3x4";
    String[] dimensions = input.split("x");
    int l = Integer.parseInt(dimensions[0]);
    int w = Integer.parseInt(dimensions[1]);
    int h = Integer.parseInt(dimensions[2]);
    assertThat(new Day02(List.of(input)).ribbon(l, w, h)).isEqualTo(34);
    assertThat(new Day02(List.of(input)).solvePart02()).isEqualTo(34);
    assertThat(new Day02(List.of("1x1x10")).solvePart02()).isEqualTo(14);
  }
}
