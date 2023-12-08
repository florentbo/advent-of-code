package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.ArrayList;
import java.util.function.Predicate;
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
    Node start =
        this.nodes.stream().filter(n -> n.arrival().equals("AAA")).findFirst().orElseThrow();
    String arrival =
        this.nodes.stream()
            .filter(n -> n.arrival().equals("ZZZ"))
            .findFirst()
            .map(Node::arrival)
            .orElseThrow();
    log.debug("arrival: {}", arrival);

    log.debug("start: {}", start);

    return searchFinish(start, arrival);
  }

  private int searchFinish(Node current, String arrival) {
    int count = 0;
    int result = 0;
    while (result == 0) {
      for (String direction : this.leftRights) {
        String next = direction.equals("R") ? current.right() : current.left();
        log.debug("next: {}", next);
        if (next.equals(arrival)) {
          result = count + 1;
          log.debug("found: {} index: {}", arrival, result);
        }
        Node nextNode =
            this.nodes.stream().filter(n -> n.arrival().equals(next)).findFirst().orElseThrow();
        log.debug("nextNode: {}", nextNode);
        current = nextNode;
        count++;
      }
    }
    return result;
  }

  @Override
  public long solvePart02() {
    Predicate<Node> arrivalPredicate = n -> n.arrival().endsWith("Z");
    List<Node> start = this.nodes.stream().filter(n -> n.arrival().endsWith("A")).toList();
    List<Node> arrival = this.nodes.stream().filter(arrivalPredicate).toList();

    log.debug("start: {}", start);
    log.debug("arrival: {}", arrival);
    return findPart2bis(start, arrival);
  }

  private int findPart2bis(List<Node> current, List<Node> arrival) {
    int count = 0;
    int result = 0;
    while (result == 0) {
      for (String direction : this.leftRights) {
        List<String> next =
            current.stream().map(n -> direction.equals("R") ? n.right() : n.left()).toList();
        log.debug("next: {}", next);
        if (next.stream().allMatch(n -> n.endsWith("Z"))) {
          result = count + 1;
          log.debug("found: {} index: {}", arrival, result);
        }
        List<Node> nextNode = next.stream().map(this::findNext).toList();
        log.debug("nextNode: {}", nextNode);
        current = nextNode;
        count++;
      }
    }
    return result;
  }

  private Node findNext(String arrival) {
    return this.nodes.stream().filter(n -> n.arrival().equals(arrival)).findFirst().orElseThrow();
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/08/2023_08_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day08 day = new Day08(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    // log.info("solution part 2: {}", day.solvePart02());
  }

  public record Node(String arrival, String left, String right) {}
}
