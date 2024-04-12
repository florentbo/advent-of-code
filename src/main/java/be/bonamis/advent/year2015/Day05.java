package be.bonamis.advent.year2015;

import static be.bonamis.advent.DayDataRetriever.dayUrl;
import static be.bonamis.advent.DayDataRetriever.downloadInput;

import be.bonamis.advent.TextDaySolver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day05 extends TextDaySolver {

  public Day05(List<String> sample) {
    super(sample);
  }

  static boolean containsThreeVowels(String input) {
    String vowels = "aeiou";
    IntStream chars = input.chars();
    return chars.filter(c -> vowels.indexOf(c) != -1).count() >= 3;
  }

  static boolean twiceInaRow(String input) {
    return IntStream.range(0, input.length() - 1)
        .mapToObj(i -> new AbstractMap.SimpleEntry<>(input.charAt(i), input.charAt(i + 1)))
        .anyMatch(
            entry -> {
              char key = entry.getKey();
              char value = entry.getValue();
              // log.info("key: {}, value: {}", key, value);
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

  @Override
  public long solvePart01() {
    String s = this.puzzle.get(0);
    return this.puzzle.stream().filter(Day05::solve).count();
  }

  @Override
  public long solvePart02() {
    return 99;
  }

  public static void main(String[] args) {
    String puzzleInputUrl = dayUrl(2015, 5) + "/input";
    InputStream inputStream = downloadInput(puzzleInputUrl);
    Day05 day05 = new Day05(lines(inputStream));
    System.out.println("Day 05");
    System.out.println("Part 1: " + day05.solvePart01());
    System.out.println("Part 2: " + day05.solvePart02());
  }

  public static List<String> lines(InputStream inputStream) {
    try (InputStream is = inputStream) {
      return new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next().lines().toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
