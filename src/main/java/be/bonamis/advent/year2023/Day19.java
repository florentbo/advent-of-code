package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day19 extends DaySolver<String> {

  private final WorkFlows workFlows;
  private final Ratings ratings;

  public Day19(List<String> puzzle) {
    super(puzzle);

    OptionalInt first =
        IntStream.range(0, this.puzzle.size())
            .filter(index -> this.puzzle.get(index).isEmpty())
            .findFirst();

    int emptyLineIndex = first.stream().boxed().toList().get(0);
    workFlows =
        new WorkFlows(
            IntStream.range(0, emptyLineIndex)
                .mapToObj(index -> WorkFlow.parse(puzzle.get(index)))
                .toList());
    ratings =
        new Ratings(
            IntStream.range(emptyLineIndex + 1, puzzle.size())
                .mapToObj(index -> Rating.parse(puzzle.get(index)))
                .toList());
  }

  @Override
  public long solvePart01() {
    return 1000L;
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  record WorkFlows(List<WorkFlow> workFlows) {
    public WorkFlow start() {
      return get("in");
    }

    WorkFlow get(String name) {
      return this.workFlows.stream()
          .filter(workFlow -> workFlow.name().equals(name))
          .findFirst()
          .orElseThrow();
    }
  }

  record Ratings(List<Rating> ratings) {}

  record WorkFlow(String name, List<Rule> rules) {
    static WorkFlow parse(String input) {
      Pattern pattern = Pattern.compile("(\\w+)\\{(.*?)\\}");

      Matcher matcher = pattern.matcher(input);

      if (matcher.find()) {
        String name = matcher.group(1);
        String rules = matcher.group(2);
        return new WorkFlow(name, Arrays.stream(rules.split(",")).map(Rule::new).toList());
      }

      return new WorkFlow(null, null);
    }

    record Rule(String value) {}
  }

  record Rating(Map<String, Integer> values) {
    static Rating parse(String input) {

      String[] pairs = input.substring(1, input.length() - 1).split(",");
      Map<String, Integer> map =
          Arrays.stream(pairs)
              .map(pair -> pair.split("="))
              .collect(Collectors.toMap(pair -> pair[0], pair -> Integer.parseInt(pair[1])));

      log.debug("map: {}", map);

      return new Rating(map);
    }
  }
}
