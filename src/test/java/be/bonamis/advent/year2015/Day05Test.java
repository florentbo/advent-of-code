package be.bonamis.advent.year2015;

import static be.bonamis.advent.year2015.Day05.*;
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
    assertThat(solve("ugknbfddgicrmopn")).isTrue();
    assertThat(solve("aaa")).isTrue();
    assertThat(solve("jchzalrnumimnmhp")).isFalse();
    assertThat(solve("haegwjzuvuyypxyu")).isFalse();
    assertThat(solve("dvszwmarrgswjxmb")).isFalse();
  }

  @Test
  void twoPairs() {
    assertThat(containsPair("xyxy")).isTrue();
    assertThat(containsPair("aabcdefgaa")).isTrue();
    assertThat(containsPair("aaa")).isFalse();
  }

  @Test
  void oneLetterBetweenOther() {
    assertThat(containsOneLetterBetweenOther("xyx")).isTrue();
    assertThat(containsOneLetterBetweenOther("abcdefeghi")).isTrue();
    assertThat(containsOneLetterBetweenOther("aaa")).isTrue();
    assertThat(containsOneLetterBetweenOther("xyz")).isFalse();
  }

  @Test
  void solvePart02() {
    assertThat(solve02("qjhvhtzxzqqjkmpb")).isTrue();
    assertThat(solve02("xxyxx")).isTrue();
    assertThat(solve02("uurcxstgmygtbstg")).isFalse();
    assertThat(solve02("ieodomkazucvgmuy")).isFalse();
  }

}
