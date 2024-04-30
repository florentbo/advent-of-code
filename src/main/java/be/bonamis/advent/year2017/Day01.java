package be.bonamis.advent.year2017;

import static be.bonamis.advent.DayDataRetriever.*;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day01 extends TextDaySolver {

  public Day01(InputStream sample) {
    super(sample);
  }

  @Override
  public long solvePart01() {
    List<Integer> digits = digits(this.puzzle.get(0));
    return matches(digits).stream().mapToInt(Integer::intValue).sum();
  }

  @Override
  public long solvePart02() {
    List<Integer> digits = digits(this.puzzle.get(0));
    return halfwayAroundMatches(digits).stream().mapToInt(Integer::intValue).sum();
  }

  static List<Integer> digits(String input) {
    return input.chars().mapToObj(Character::getNumericValue).toList();
  }

  static List<Integer> matches(List<Integer> digits) {
    int size = digits.size();
    return IntStream.range(0, digits.size())
        .filter(i -> digits.get(i).equals(digits.get((i + 1) % size)))
        .mapToObj(digits::get)
        .toList();
  }

  static List<Integer> halfwayAroundMatches(List<Integer> digits) {
    int size = digits.size();
    int half = size / 2;
    return IntStream.range(0, digits.size())
        .filter(i -> digits.get(i).equals(digits.get((i + half) % size)))
        .mapToObj(digits::get)
        .toList();
  }
}
