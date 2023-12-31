package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;
import java.util.HashMap;
import java.util.stream.Stream;

import be.bonamis.advent.utils.marsrover.*;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.year2023.Day17.Parts.*;

@Slf4j
@Getter
public class Day17 extends DaySolver<String> {

  private final CharGrid charGrid;

  public Day17(List<String> puzzle) {
    super(puzzle);
    charGrid = new CharGrid(puzzle);
  }

  enum Parts {
    ONE,
    TWO
  }

  @Override
  public long solvePart01() {
    return solve(ONE);
  }

  private Integer solve(Parts parts) {
    Position startPosition = Position.of(0, 0);
    log.info("startPosition {} value {} ", startPosition, value(startPosition));

    Position endPosition = Position.of(charGrid.getWidth() - 1, charGrid.getHeight() - 1);
    log.info("endPosition {} value {} ", endPosition, value(endPosition));

    Integer shortestPath = shortestPath(startPosition, endPosition, parts);
    log.info("shortestPath: {}", shortestPath);

    return shortestPath;
  }

  record Crucible(Rover rover, int previous) {}

  record VertexPair(Crucible crucible, Integer weight) implements Comparable<VertexPair> {
    @Override
    public int compareTo(VertexPair o) {
      return Integer.compare(this.weight, o.weight);
    }
  }

  Integer shortestPath(Position src, Position dest, Parts part) {
    PriorityQueue<VertexPair> pq = new PriorityQueue<>();
    Map<Crucible, Integer> dist = new HashMap<>();
    Crucible start = fromDirection(new Rover(Direction.NORTH, src), 0);
    pq.add(new VertexPair(start, 0));
    dist.put(start, 0);
    Set<Integer> set = new TreeSet<>();
    while (!pq.isEmpty()) {
      VertexPair vertexPair = pq.poll();
      Crucible crucible = vertexPair.crucible();
      if (crucible.rover().position().equals(dest)) {
        log.debug("found shortest path");
        Integer distance = dist.get(crucible);
        log.debug("distance: {}", distance);
        set.add(distance);
      }
      neighbors(crucible, part)
          .forEach(
              neighbor -> {
                Position neighborPosition = neighbor.rover().position();

                Integer actualDistance = dist.get(crucible);
                int weight = value(neighborPosition);
                int newDistance = actualDistance + weight;

                if (dist.getOrDefault(neighbor, Integer.MAX_VALUE) > newDistance) {
                  dist.put(neighbor, newDistance);
                  pq.add(new VertexPair(neighbor, weight));
                }
              });
    }
    return set.iterator().next();
  }

  Stream<Crucible> neighbors(Crucible crucible, Parts parts) {
    Rover rover = crucible.rover();
    Rover same = rover.move(FORWARD, true);
    Direction sameDirection = same.direction();
    Direction inverse = sameDirection.inverse();
    Stream<Crucible> others =
        Arrays.stream(Direction.values())
            .filter(direction -> !direction.equals(inverse) && !direction.equals(sameDirection))
            .map(direction -> fromNewDirection(direction, rover));
    return findNeighbors(crucible, others, same, parts).filter(this::isPositionInTheGrid);
  }

  private Crucible fromNewDirection(Direction direction, Rover rover) {
    return fromDirection(new Rover(direction, rover.position()).move(FORWARD, true), 1);
  }

  private Crucible fromDirection(Rover direction, int previous) {
    return new Crucible(direction, previous);
  }

  private Stream<Crucible> findNeighbors(Crucible crucible, Stream<Crucible> others, Rover same, Parts part) {
    return crucible.previous() + 1 > 3
        ? others
        : Stream.concat(others, Stream.of(fromDirection(same, crucible.previous() + 1)));
  }

  private boolean isPositionInTheGrid(Crucible crucible2) {
    return charGrid.isPositionInTheGrid(crucible2.rover());
  }

  private int value(Position node) {
    char data = charGrid.get(node);
    return Character.getNumericValue(data);
  }

  @Override
  public long solvePart02() {
    return solve(TWO);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/17/2023_17_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day17 day = new Day17(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
