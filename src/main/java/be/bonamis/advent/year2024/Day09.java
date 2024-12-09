package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day09 extends TextDaySolver {

  public Day09(InputStream inputStream) {
    super(inputStream);
  }

  public Day09(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    log.info("size: {}", this.puzzle.size());
    String line = this.puzzle.get(0);
    log.info("line: {}", line);
    int length = line.length();
    log.info("line length: {}", length);

    List<String> compacted = compact(line);
    log.info("compacted: {}", compacted);
    log.debug("compacted size: {}", compacted.size());

    String joined = String.join("", compacted);
    int lefterDotPosition = lefterDotPosition(joined);
    log.debug("multiplying:+++++++++++ till lefterDotPosition: {}", lefterDotPosition);
    Long sum =
        IntStream.range(0, lefterDotPosition)
            .mapToObj(
                i -> {
                  long value = Character.getNumericValue(joined.charAt(i));
                  log.info("value: {} at index: {}", value, i);
                  return value * i;
                })
            .reduce(0L, Long::sum);
    log.debug("list: {}", sum);

    return sum;
  }

  static List<String> compact(String line) {
    List<Integer> list = line.chars().mapToObj(Character::toString).map(Integer::parseInt).toList();

    List<String> list1 =
        IntStream.range(0, list.size())
            .mapToObj(
                index -> {
                  Integer value = list.get(index);
                  // log.debug("value: {}", value);
                  if (index % 2 == 0) {
                    int evenValueIndex = index / 2;
                    // log.debug("evenValueIndex: {}", evenValueIndex);
                    String numbers =
                        String.join("", Collections.nCopies(value, evenValueIndex + ""));
                    // log.debug("numbers: {}", numbers);
                    return numbers;
                  } else {
                    String dots = String.join("", Collections.nCopies(value, "."));
                    // log.debug("dots: {}", dots);
                    return dots;
                  }
                })
            .toList();
    log.debug("list1: {}", list1);
    /*
    0..111....22222
     */
    String joined = String.join("", list1);
    log.debug("joined: {}", joined);

    char[] chars = joined.toCharArray();
    int left = 0;
    int right = chars.length - 1;


    while (left < chars.length && chars[left] != '.') left++;
    while (right >= 0 && chars[right] == '.') right--;

    while (left < right) {
      if (chars[left] == '.' && Character.isDigit(chars[right])) {
        chars[left] = chars[right];
        chars[right] = '.';
        left++;
        right--;
      }
      while (left < chars.length && chars[left] != '.') left++;
      while (right >= 0 && chars[right] == '.') right--;
    }

    return Arrays.stream(new String(chars).split("")).toList();
  }

  static int righterNumberPosition(String line) {
    return IntStream.range(0, line.length())
        .mapToObj(i -> line.length() - 1 - i) // reverse indices
        .filter(i -> Character.isDigit(line.charAt(i)))
        .findFirst()
        .orElseThrow();
  }

  static int lefterDotPosition(String line) {
    return IntStream.range(0, line.length())
        .boxed()
        .filter(i -> line.charAt(i) == '.')
        .findFirst()
        .orElseThrow();
  }

  @Override
  public long solvePart02() {
    return 4;
  }
}
