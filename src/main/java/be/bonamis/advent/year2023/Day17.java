package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;

import be.bonamis.advent.utils.marsrover.*;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import be.bonamis.advent.year2023.poc.DijkstraAlgorithm;
import be.bonamis.advent.year2023.poc.Node;
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
    Map<Position, Node<Position>> nodes = new HashMap<>();
    charGrid.consume(
        point -> {
          Position position = Position.of(point);
          Node<Position> node = new Node<>(position);
          nodes.put(position, node);
        });

    Position startPosition = Position.of(0, 0);
    log.info("startPosition {} value {} ", startPosition, value(startPosition));

    Position endPosition = Position.of(charGrid.getWidth() - 1, charGrid.getHeight() - 1);
    log.info("endPosition {} value {} ", endPosition, value(endPosition));
    Node<Position> sourceNode = nodes.get(startPosition);
    Node<Position> sinkNode = nodes.get(endPosition);

    List<Node<Position>> shortestPath =
        new PositionDijkstraAlgorithm(charGrid, nodes).findShortestPath(sourceNode, sinkNode);
    shortestPath.forEach(p -> log.debug("pos {} value {}", p, value(p.getValue())));

    Optional<Integer> sum =
        shortestPath.stream().map(node -> value(node.getValue())).reduce(Integer::sum);
    sum.ifPresent(x -> log.info("sum distance {}", x));

    int end = value(endPosition);
    int start = value(startPosition);
    log.info("start {} end {}", start, end);
    return sum.orElseThrow() - start;
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

  static class PositionDijkstraAlgorithm extends DijkstraAlgorithm<Position> {
    private final CharGrid charGrid;
    private final Map<Position, Node<Position>> nodes;

    public PositionDijkstraAlgorithm(CharGrid charGrid, Map<Position, Node<Position>> nodes) {
      this.charGrid = charGrid;
      this.nodes = nodes;
    }

    record Crucible(Rover rover, List<Rover> previous) {}

    void shortestPath(Crucible src, Crucible dest) {
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
          break;
        }
        List<Rover> previous = polled.previous;
        for (Rover neighbor : neighbors(polled)) {
          Position neighborPosition = neighbor.position();
          int weight = value(neighborPosition);
          Rover polledRover = polled.rover();
          int newDistance = dist.get(polledRover.position()) + weight;
          if (dist.get(neighborPosition) > newDistance) {
            dist.put(neighborPosition, newDistance);
            previous.add(polledRover);
            pq.add(new Crucible(neighbor, previous));
          }
        }
      }
    }

    public List<Node<Position>> findShortestPath(Node<Position> start, Node<Position> end) {

      Map<Node<Position>, Integer> distanceMap = new LinkedHashMap<>();
      Map<Node<Position>, Node<Position>> parentMap = new LinkedHashMap<>();
      PriorityQueue<Node<Position>> priorityQueue =
          new PriorityQueue<>(Comparator.comparingInt(distanceMap::get));

      distanceMap.put(start, 0);
      priorityQueue.add(start);

      while (!priorityQueue.isEmpty()) {
        Node<Position> current = priorityQueue.poll();

        if (current == end) {
          return reconstructPath(parentMap, end);
        }

        List<Node<Position>> reconstructPath = reconstructPath(parentMap, current);
        Iterable<Map.Entry<Node<Position>, Integer>> neighbors = neighbors(current);
        for (Map.Entry<Node<Position>, Integer> entry : neighbors) {
          Node<Position> neighbor = entry.getKey();
          int weight = entry.getValue();
          int newDistance = distanceMap.get(current) + weight;

          boolean cond01 = !distanceMap.containsKey(neighbor);
          boolean cond02 =
              distanceMap.containsKey(neighbor) && newDistance <= distanceMap.get(neighbor);
          if (cond01 || cond02) {
            if (canContinueInTheSameDirection(reconstructPath, neighbor.getValue())) {
              distanceMap.put(neighbor, newDistance);
              parentMap.put(neighbor, current);
              priorityQueue.add(neighbor);
            }
          }
        }
      }

      return Collections.emptyList();
    }

    private List<Node<Position>> reconstructPath(
        Map<Node<Position>, Node<Position>> parentMap, Node<Position> end) {
      List<Node<Position>> path = new ArrayList<>();
      Node<Position> current = end;

      while (current != null) {
        path.add(current);
        current = parentMap.get(current);
      }

      Collections.reverse(path);
      return path;
    }

    Iterable<Map.Entry<Node<Position>, Integer>> neighbors(Node<Position> current) {
      Set<Position> positions = allowedPositions(current.getValue());
      return positions.stream().collect(toMap(this.nodes::get, this::value)).entrySet();
    }

    private int value(Position node) {
      char data = charGrid.get(node);
      return Character.getNumericValue(data);
    }

    List<Rover> neighbors(Crucible crucible) {
      Rover rover = crucible.rover();
      List<Rover> previous = crucible.previous();
      Set<Position> positions = allowedPositions(rover.position());
      return positions.stream()
          .map(position -> new Rover(rover.direction(), position))
          .collect(toList());
    }

    private Set<Position> allowedPositions(Position currentPosition) {
      return Arrays.stream(Direction.values())
          .map(direction -> new Rover(direction, currentPosition).move(FORWARD, true))
          .filter(charGrid::isPositionInTheGrid)
          .map(Rover::position)
          .collect(toSet());
    }

    private boolean canContinueInTheSameDirection(
        List<Node<Position>> currentShortestPath, Position neighbor) {
      List<Position> positions = currentShortestPath.stream().map(Node::getValue).toList();
      if (positions.contains(neighbor)) {
        return false;
      }
      int size = 3;
      if (positions.size() >= size) {
        List<Position> latest3AndActual =
            new ArrayList<>(positions.subList(positions.size() - size, positions.size()));
        if (!latest3AndActual.contains(new Position(0, 0))) {
          latest3AndActual.add(neighbor);
        }
        Set<Long> xValues = latest3AndActual.stream().map(Position::x).collect(toSet());
        Set<Long> yValues = latest3AndActual.stream().map(Position::y).collect(toSet());
        boolean allTheSame = xValues.size() == 1 || yValues.size() == 1;
        return !allTheSame;
      }
      return true;
    }
  }
}
