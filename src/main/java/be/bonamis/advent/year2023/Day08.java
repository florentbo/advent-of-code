package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day08 extends DaySolver<String> {

  private final List<String> leftRights;
  private final List<Node> nodes;

  public Day08(List<String> puzzle) {
    super(puzzle);
    leftRights = Arrays.stream(this.puzzle.get(0).split("")).toList();
    log.debug("leftRights: {}", leftRights);
    nodes =
        IntStream.range(2, this.puzzle.size()).mapToObj(i -> parse(this.puzzle.get(i))).toList();
    log.debug("nodes: {}", nodes);
  }

  Node parse(String input) {
    String[] split = input.split(" = ");
    String leftRight = split[1];
    return new Node(split[0], leftRight.substring(1, 4), leftRight.substring(6, 9));
  }

  @Override
  public long solvePart01() {
    Node start = this.nodes.stream().filter(n -> n.arrival().equals("AAA")).findFirst().orElseThrow();
    String arrival = "ZZZ";
    log.debug("arrival: {}", arrival);
    List<String> path = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      path.addAll(this.leftRights);
    }
    log.debug("start: {}", start);

    return searchFinish(path, start, arrival);
  }

  private int searchFinish(List<String> path, Node current, String arrival) {
    for (int i = 0; i < path.size(); i++) {
      String direction = path.get(i);
      String next = direction.equals("R") ? current.right() : current.left();
      log.debug("next: {}", next);
      if (next.equals(arrival)) {
        int result = i + 1;
        log.debug("found: {} index: {}", arrival, result);
        return result;
      }
      Node nextNode =
          this.nodes.stream().filter(n -> n.arrival().equals(next)).findFirst().orElseThrow();
      log.debug("nextNode: {}", nextNode);
      current = nextNode;
    }
    return 0;
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/08/2023_08_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day08 day = new Day08(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  public record Node(String arrival, String left, String right) {}
}
