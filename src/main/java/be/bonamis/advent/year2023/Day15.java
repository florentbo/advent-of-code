package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day15 extends DaySolver<String> {

  private final Stream<String> lenses;

  public Day15(String puzzle) {
    super(Collections.singletonList(puzzle));
    List<String> list = Arrays.asList(this.puzzle.get(0).split(","));
    log.info("list size: {}", list.size());
    lenses = list.stream().map(String::strip);
  }

  @Override
  public long solvePart01() {
    return lenses.map(Day15::hash).reduce(Long::sum).orElseThrow();
  }

  Map<Long, Map<String, Long>> boxes(int i) {
    Map<Long, Map<String, Long>> boxes = new HashMap<>();
    lenses
        .limit(i)
        .map(Lens::of)
        .forEach(
            lens -> {
              long hash = lens.hash();
              if (lens.operation() == '=') {
                Map<String, Long> box = boxes.computeIfAbsent(hash, k -> new LinkedHashMap<>());
                box.put(lens.label(), lens.length());
              } else {
                if (boxes.containsKey(hash)) {
                  boxes.get(hash).remove(lens.label());
                  if (boxes.get(hash).isEmpty()) {
                    boxes.remove(hash);
                  }
                }
              }
            });
    return boxes;
  }

  static long hash(String input) {
    return input.chars().mapToLong(i -> i).reduce(0, Day15::modify);
  }

  static long modify(long start, long hChar) {
    return ((start + hChar) * 17) % 256;
  }

  @Override
  public long solvePart02() {
    return 99;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/15/2023_15_input.txt");
    Day15 day = new Day15(content);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  record Lens(String label, char operation, long length) {

    long hash() {
      return Day15.hash(label);
    }

    static Lens of(String input) {
      Pattern pattern = Pattern.compile("([a-zA-Z]+)([-=])(\\d*)");
      Matcher matcher = pattern.matcher(input);

      if (matcher.matches()) {
        String label = matcher.group(1);
        char operation = matcher.group(2).charAt(0);
        long length = matcher.group(3).isEmpty() ? 0 : Long.parseLong(matcher.group(3));

        return new Lens(label, operation, length);
      } else {
        throw new IllegalArgumentException("Invalid input format: " + input);
      }
    }
  }
}
