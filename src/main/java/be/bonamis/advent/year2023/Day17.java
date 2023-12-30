package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;
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

    Crucible src = new Crucible(new Rover(Direction.EAST, startPosition), new ArrayList<>());
    Crucible dest = new Crucible(new Rover(Direction.EAST, endPosition), new ArrayList<>());
    return shortestPath(src, dest);
  }

  record Crucible(Rover rover, List<Rover> previous) {}

  Integer shortestPath(Crucible src, Crucible dest) {
    PriorityQueue<Crucible> pq =
        new PriorityQueue<>(
            (r1, r2) ->
                Integer.compare(value(r1.rover().position()), value(r2.rover().position())));
    Map<Position, Integer> dist =
        this.charGrid.stream().collect(toMap(Position::of, p -> Integer.MAX_VALUE));
    pq.add(src);
    dist.put(src.rover().position(), 0);
    while (!pq.isEmpty()) {
      Crucible polled = pq.poll();
      if (polled.rover().position().equals(dest.rover().position())) {
        log.info("found shortest path");
        Integer dist01 = dist.get(polled.rover().position());
        log.info("dist 01: {}", dist01);
        return dist01;
      }
      Collection<Rover> neighbors = neighbors(polled);
      Rover polledRover = polled.rover();
      for (Rover neighbor : neighbors) {
        Position neighborPosition = neighbor.position();

        int weight = value(neighborPosition);
        int newDistance = dist.get(polledRover.position()) + weight;

        if (dist.get(neighborPosition) > newDistance) {
          dist.put(neighborPosition, newDistance);

          List<Rover> newPrevious = new ArrayList<>(polled.previous);
          newPrevious.add(polledRover);

          pq.add(new Crucible(neighbor, newPrevious));
        }
      }
    }
    return null;
  }

  List<Rover> neighbors(Crucible crucible) {
    Rover rover = crucible.rover();
    List<Rover> previous = new ArrayList<>(crucible.previous());
    previous.add(rover);
    List<Position> positions = previous.stream().map(Rover::position).toList();

    Rover same = rover.move(FORWARD, true);
    Direction inverse = same.direction().inverse();
    Stream<Rover> others =
        Arrays.stream(Direction.values())
            .filter(direction -> !direction.equals(inverse) && !direction.equals(same.direction()))
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
