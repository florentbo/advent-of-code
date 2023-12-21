package be.bonamis.advent.year2023;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.stream.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day20 extends TextDaySolver {

  private final Map<String, List<String>> moduleConfiguration;

  public Day20(List<String> puzzle) {
    super(puzzle);
    this.moduleConfiguration = readConfiguration();
  }

  public Day20(String sample) {
    super(sample);
    this.moduleConfiguration = readConfiguration();
  }

  private Map<String, List<String>> readConfiguration() {
    return this.puzzle.stream()
        .map(pair -> pair.split("->"))
        .collect(Collectors.toMap(pair -> pair[0].strip(), pair -> toList(pair[1].strip())));
  }

  private List<String> toList(String destinations) {
    return Arrays.stream(destinations.split(",")).map(String::strip).toList();
  }

  Map<String, List<String>> fliFlops() {
    return filterModules("%");
  }

  Map<String, List<String>> conjunctions() {
    return filterModules("&");
  }

  private Map<String, List<String>> filterModules(String prefix) {
    return this.moduleConfiguration.entrySet().stream()
        .filter(entry -> entry.getKey().contains(prefix))
        .collect(Collectors.toMap(entry -> entry.getKey().substring(1), Map.Entry::getValue));
  }

  @Override
  public long solvePart01() {
    return 101L;
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/19/2023_19_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day20 day = new Day20(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
