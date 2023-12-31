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
import static java.util.stream.Collectors.*;

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

    //    Crucible src = new Crucible(new Rover(Direction.NORTH, startPosition), new ArrayList<>());

    Integer shortestPath = shortestPath(startPosition, endPosition);
    log.info("shortestPath: {}", shortestPath);

    return shortestPath;
  }

  record Crucible(Rover rover, List<Rover> previous) {}

  record Crucible2(Rover rover, int previous) {}

  Integer shortestPath(Position src, Position dest) {
    PriorityQueue<Crucible2> pq =
        new PriorityQueue<>(
            (r1, r2) ->
                Integer.compare(value(r2.rover().position()), value(r1.rover().position())));
    Map<Crucible2, Integer> dist = new HashMap<>();
    Crucible2 start = new Crucible2(new Rover(Direction.NORTH, src), 0);
    pq.add(start);
    dist.put(start, value(src));
    while (!pq.isEmpty()) {
      Crucible2 polled = pq.poll();
      if (polled.rover().position().equals(dest)) {
        log.info("found shortest path");
        Integer dist01 = dist.get(polled);
        log.info("dist 01: {}", dist01);
      }
      Stream<Crucible2> neighbors = neighbors(polled);
      neighbors.forEach(
          neighbor -> {
            Position neighborPosition = neighbor.rover().position();

            int weight = value(neighborPosition);
            Integer actualDistance = dist.get(polled);
            int newDistance = actualDistance + weight;

            if (dist.getOrDefault(neighbor, Integer.MAX_VALUE) > newDistance) {
              dist.put(neighbor, newDistance);
              pq.add(neighbor);
            }
          });
    }
    return dist.entrySet().stream()
        .filter(e -> e.getKey().rover().position().equals(dest))
        .findFirst()
        .map(Map.Entry::getValue)
        .orElseThrow();
  }

  Integer shortestPath(Crucible src, Position dest) {
    PriorityQueue<Crucible> pq =
        new PriorityQueue<>(
            (r1, r2) ->
                Integer.compare(value(r2.rover().position()), value(r1.rover().position())));
    Map<Position, Integer> dist =
        this.charGrid.stream().collect(toMap(Position::of, p -> Integer.MAX_VALUE));
    pq.add(src);
    dist.put(src.rover().position(), value(src.rover().position()));
    while (!pq.isEmpty()) {
      Crucible polled = pq.poll();
      if (polled.rover().position().equals(dest)) {
        log.info("found shortest path");
        Integer dist01 = dist.get(polled.rover().position());
        log.info("dist 01: {}", dist01);
        // return dist01;
      }
      Collection<Rover> neighbors = neighbors(polled);
      Rover polledRover = polled.rover();
      for (Rover neighbor : neighbors) {
        Position neighborPosition = neighbor.position();

        int weight = value(neighborPosition);
        Integer actualDistance = dist.get(polledRover.position());
        int newDistance = actualDistance + weight;

        if (dist.get(neighborPosition) > newDistance) {
          dist.put(neighborPosition, newDistance);

          List<Rover> newPrevious = new ArrayList<>(polled.previous);
          newPrevious.add(polledRover);

          pq.add(new Crucible(neighbor, newPrevious));
        }
      }
    }
    // dist.forEach((k, v) -> log.info("key {} value {} ", k, v));
    return dist.get(dest);
  }

  Stream<Crucible2> neighbors(Crucible2 crucible) {
    Rover rover = crucible.rover();
    Rover same = rover.move(FORWARD, true);
    Direction sameDirection = same.direction();
    Direction inverse = sameDirection.inverse();
    Stream<Crucible2> others =
        Arrays.stream(Direction.values())
            .filter(direction -> !direction.equals(inverse) && !direction.equals(sameDirection))
            .map(
                direction ->
                    new Crucible2(new Rover(direction, rover.position()).move(FORWARD, true), 1));
    int previous = crucible.previous() + 1;
    return (previous > 3 ? others : Stream.concat(others, Stream.of(new Crucible2(same, previous))))
        .filter(this::isPositionInTheGrid);
  }

  private boolean isPositionInTheGrid(Crucible2 crucible2) {
    return charGrid.isPositionInTheGrid(crucible2.rover());
  }

  List<Rover> neighbors(Crucible crucible) {
    Rover rover = crucible.rover();
    List<Rover> previous = new ArrayList<>(crucible.previous());
    previous.add(rover);
    List<Position> positions = previous.stream().map(Rover::position).toList();

    Rover same = rover.move(FORWARD, true);
    Direction sameDirection = same.direction();
    Direction inverse = sameDirection.inverse();
    Stream<Rover> others =
        Arrays.stream(Direction.values())
            .filter(direction -> !direction.equals(inverse) && !direction.equals(sameDirection))
            .map(direction -> new Rover(direction, rover.position()).move(FORWARD, true));

    int size = 3;

    return (previous.size() >= size && allTheSame(previous, size)
            ? others
            : Stream.concat(others, Stream.of(same)))
        .filter(charGrid::isPositionInTheGrid)
        .filter(r -> !positions.contains(r.position()))
        .toList();
  }

  private boolean allTheSame(List<Rover> previous, int size) {
    List<Rover> latest3 =
        new ArrayList<>(previous.subList(previous.size() - size, previous.size()));
    Set<Direction> directions = latest3.stream().map(Rover::direction).collect(toSet());
    return directions.size() == 1;
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
