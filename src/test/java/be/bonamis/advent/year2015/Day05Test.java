package be.bonamis.advent.year2015;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Day05Test {

  @Test
  void containsThreeVowels() {
    assertThat(Day05.containsThreeVowels("aei")).isTrue();
    assertThat(Day05.containsThreeVowels("xazegov")).isTrue();
    assertThat(Day05.containsThreeVowels("aeiouaeiouaeiou")).isTrue();
    assertThat(Day05.containsThreeVowels("dvszwmarrgswjxmb")).isFalse();
    assertThat(Day05.containsThreeVowels("aaa")).isTrue();
  }

  @Test
  void twiceInaRow() {
    assertThat(Day05.twiceInaRow("abcdde")).isTrue();
    assertThat(Day05.twiceInaRow("abcde")).isFalse();
    assertThat(Day05.twiceInaRow("aaa")).isTrue();
  }

  @Test
  void notContainForbidden() {
    assertThat(Day05.notContainForbidden("axy")).isFalse();
    assertThat(Day05.notContainForbidden("aaa")).isTrue();
  }

  @Test
  void solvePart01() {
    assertThat(Day05.solve("ugknbfddgicrmopn")).isTrue();
    assertThat(Day05.solve("aaa")).isTrue();
    assertThat(Day05.solve("jchzalrnumimnmhp")).isFalse();
    assertThat(Day05.solve("haegwjzuvuyypxyu")).isFalse();
    assertThat(Day05.solve("dvszwmarrgswjxmb")).isFalse();
  }
}
