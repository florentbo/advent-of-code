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

@Slf4j
@Getter
public class Day17 extends DaySolver<String> {

  private final CharGrid charGrid;

  public Day17(List<String> puzzle) {
    super(puzzle);
    charGrid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    Position startPosition = Position.of(0, 0);
    log.info("startPosition {} value {} ", startPosition, value(startPosition));

    Position endPosition = Position.of(charGrid.getWidth() - 1, charGrid.getHeight() - 1);
    log.info("endPosition {} value {} ", endPosition, value(endPosition));

    Integer shortestPath = shortestPath(startPosition, endPosition);
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

  Integer shortestPath(Position src, Position dest) {
    PriorityQueue<VertexPair> pq = new PriorityQueue<>();
    Map<Crucible, Integer> dist = new HashMap<>();
    Crucible start = new Crucible(new Rover(Direction.NORTH, src), 0);
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
      neighbors(crucible)
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

  Stream<Crucible> neighbors(Crucible crucible) {
    Rover rover = crucible.rover();
    Rover same = rover.move(FORWARD, true);
    Direction sameDirection = same.direction();
    Direction inverse = sameDirection.inverse();
    Stream<Crucible> others =
        Arrays.stream(Direction.values())
            .filter(direction -> !direction.equals(inverse) && !direction.equals(sameDirection))
            .map(
                direction ->
                    new Crucible(new Rover(direction, rover.position()).move(FORWARD, true), 1));
    int previous = crucible.previous() + 1;
    return (previous > 3 ? others : Stream.concat(others, Stream.of(new Crucible(same, previous))))
        .filter(this::isPositionInTheGrid);
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
    return 999;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/17/2023_17_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day17 day = new Day17(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
