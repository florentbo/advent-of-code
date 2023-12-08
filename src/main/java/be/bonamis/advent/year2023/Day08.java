package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.math.BigInteger;
import java.util.*;
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
    List<Node> start = this.nodes.stream().filter(n -> n.arrival().endsWith("A")).toList();

    log.info("start: {}", start);
    long part2bis = findPart2bis(start.get(0));
    log.info("part2bis: {}", part2bis);
    List<Long> list = start.stream().map(this::findPart2bis).toList();
    log.info("list: {}", list);

    BigInteger[] array =
            list.stream().map(i -> new BigInteger(String.valueOf(i))).toArray(BigInteger[]::new);
      return lcm(array).longValue();

    // return findPart2bis(start);
  }

  BigInteger lcm(BigInteger[] input) {
    BigInteger result = input[0];
    for (int i = 1; i < input.length; i++) result = lcm(result, input[i]);
    return result;
  }

 /* public int lcm(int number1, int number2) {
    if (number1 == 0 || number2 == 0) {
      return 0;
    }
    int absNumber1 = Math.abs(number1);
    int absNumber2 = Math.abs(number2);
    int absHigherNumber = Math.max(absNumber1, absNumber2);
    int absLowerNumber = Math.min(absNumber1, absNumber2);
    int lcm = absHigherNumber;
    while (lcm % absLowerNumber != 0) {
      lcm += absHigherNumber;
    }
    return lcm;
  }*/

  public BigInteger lcm(BigInteger number1, BigInteger number2) {
    BigInteger gcd = number1.gcd(number2);
    BigInteger absProduct = number1.multiply(number2).abs();
    return absProduct.divide(gcd);
  }

  private int findPart2bis(List<Node> current) {
    int count = 0;
    int result = 0;
    while (result == 0) {
      for (String direction : this.leftRights) {
        List<String> next =
            current.stream().map(n -> direction.equals("R") ? n.right() : n.left()).toList();
        log.debug("next: {}", next);
        if (next.stream().allMatch(n -> n.endsWith("Z"))) {
          result = count + 1;
          log.debug("found: {} index: {}", next, result);
        }
        List<Node> nextNode = next.stream().map(this::findNext).toList();
        log.debug("nextNode: {}", nextNode);
        current = nextNode;
        count++;
      }
    }
    return result;
  }

  private long findPart2bis(Node current) {
    int count = 0;
    int result = 0;
    while (result == 0) {
      for (String direction : this.leftRights) {
        String next = direction.equals("R") ? current.right() : current.left();
        log.debug("next: {}", next);
        if (next.endsWith("Z")) {
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

  private Node findNext(String arrival) {
    return this.nodes.stream().filter(n -> n.arrival().equals(arrival)).findFirst().orElseThrow();
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
