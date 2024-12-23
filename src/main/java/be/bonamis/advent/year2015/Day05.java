package be.bonamis.advent.year2015;


import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day05 extends TextDaySolver {

  public Day05(InputStream sample) {
    super(sample);
  }

  static boolean containsThreeVowels(String input) {
    String vowels = "aeiou";
    IntStream chars = input.chars();
    return chars.filter(c -> vowels.indexOf(c) != -1).count() >= 3;
  }

  static boolean twiceInaRow(String input) {
    return IntStream.range(0, input.length() - 1)
        .mapToObj(i -> new SimpleEntry<>(input.charAt(i), input.charAt(i + 1)))
        .anyMatch(
            entry -> {
              char key = entry.getKey();
              char value = entry.getValue();
              return key == value;
            });
  }

  static boolean notContainForbidden(String input) {
    String forbidden = "ab, cd, pq, xy";
    String[] forbiddenWords = forbidden.split(", ");
    return Arrays.stream(forbiddenWords).noneMatch(input::contains);
  }

  static boolean solve(String input) {
    return containsThreeVowels(input) && twiceInaRow(input) && notContainForbidden(input);
  }

  static boolean containsPair(String input) {
    int inputLength = input.length();
    Stream<Pair> pairs =
        IntStream.range(0, inputLength - 1)
            .mapToObj(i -> new Pair(input.charAt(i), input.charAt(i + 1), i));
    return pairs.anyMatch(
        pair -> {
          log.debug("pair: {}", pair);
          String letters = pair.letters();
          return IntStream.range(pair.startIndex() + 2, inputLength - 1)
              .mapToObj(ii -> new Pair(input.charAt(ii), input.charAt(ii + 1), ii))
              .anyMatch(iii -> letters.equals(iii.letters()));
        });
  }

  static boolean containsOneLetterBetweenOther(String input) {
    return IntStream.range(0, input.length() - 2)
        .mapToObj(i -> new Pair(input.charAt(i), input.charAt(i + 2), i))
        .anyMatch(pair -> pair.first == pair.second);
  }

  static boolean solve02(String input) {
    return containsPair(input) && containsOneLetterBetweenOther(input);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.stream().filter(Day05::solve).count();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.stream().filter(Day05::solve02).count();
  }

  record Pair(Character first, Character second, int startIndex) {
    String letters() {
      return first + String.valueOf(second);
    }
  }
}
