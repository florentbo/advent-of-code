package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import java.util.*;
import java.util.ArrayList;
import java.util.function.IntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day19 extends DaySolver<String> {

  private final List<WorkFlow> workFlows;
  private final List<Rating> ratings;

  public Day19(List<String> puzzle) {
    super(puzzle);

    OptionalInt first =
        IntStream.range(0, this.puzzle.size())
            .filter(index -> this.puzzle.get(index).isEmpty())
            .findFirst();

    int emptyLineIndex = first.stream().boxed().toList().get(0);
    workFlows =
        IntStream.range(0, emptyLineIndex)
            .mapToObj(index -> WorkFlow.parse(puzzle.get(index)))
            .toList();
    ratings =
        IntStream.range(emptyLineIndex + 1, puzzle.size())
            .mapToObj(index -> Rating.parse(puzzle.get(index)))
            .toList();
  }

  @Override
  public long solvePart01() {
    return 1000L;
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

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
