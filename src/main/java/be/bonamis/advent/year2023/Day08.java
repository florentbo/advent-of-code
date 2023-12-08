package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.math.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

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

    log.debug("start: {}", start);

    return searchFinish(start, s -> s.equals("ZZZ"));
  }

  @Override
  public long solvePart02() {
    List<Node> start = this.nodes.stream().filter(n -> n.arrival().endsWith("A")).toList();
    log.info("start: {}", start);

    List<Long> list =
        start.stream().map(current -> searchFinish(current, s -> s.endsWith("Z"))).toList();
    log.info("list: {}", list);

    return lcm(list);
  }

  private long searchFinish(Node current, Predicate<String> predicate) {
    int count = 0;
    int result = 0;

    while (result == 0) {
      for (String direction : this.leftRights) {
        String next = direction.equals("R") ? current.right() : current.left();
        log.debug("next: {}", next);
        if (predicate.test(next)) {
          result = count + 1;
          log.debug("found: {} index: {}", next, result);
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

  Long lcm(List<Long> input) {
    return input.stream()
        .map(BigInteger::valueOf)
        .reduce(BigInteger.valueOf(1L), this::lcm)
        .longValue();
  }

  public BigInteger lcm(BigInteger a, BigInteger b) {
    BigInteger gcd = a.gcd(b);
    return a.multiply(b).divide(gcd);
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
