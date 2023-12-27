package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;

import be.bonamis.advent.utils.marsrover.*;
import be.bonamis.advent.year2023.poc.DijkstraAlgorithm;
import be.bonamis.advent.year2023.poc.DijkstraAlgorithm.Result;
import be.bonamis.advent.year2023.poc.Node;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.values;
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

    Result<Position> result =
        new PositionDijkstraAlgorithm(charGrid, nodes)
            .calculateShortestPathFromSource(sourceNode, sinkNode, false);
    //result.path().forEach(p -> log.debug("pos {} value {}", p, value(p.getValue())));
    Optional<Integer> sum =
        result.path().stream().map(node -> value(node.getValue())).reduce(Integer::sum);
    sum.ifPresent(
        x -> {
          System.out.println(x);
          log.info("sum distance {}", x);
        });

    int distance = result.distance();
    log.info("result distance {}", distance);

    int end = value(endPosition);
    int start = value(startPosition);
    log.info("start {} end {}", start, end);
    return distance + end;
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

    @Override
    protected Map<Node<Position>, Integer> adjacentNodes(Node<Position> currentNode) {
      List<Node<Position>> currentShortestPath = currentNode.getShortestPath();
      Set<Position> positions = allowedPositions(currentNode.getValue(), currentShortestPath);
      return positions.stream().collect(toMap(this.nodes::get, this::value));
    }

    private int value(Position node) {
      char data = charGrid.get(node);
      return Character.getNumericValue(data);
    }

    private Set<Position> allowedPositions(
        Position startPosition, List<Node<Position>> currentShortestPath) {
      return Arrays.stream(values())
          .map(
              direction -> {
                Rover rover = new Rover(direction, startPosition);
                return rover.move(FORWARD, true);
              })
          .filter(charGrid::isPositionInTheGrid)
          .map(Rover::position)
          .filter(position -> canContinueInTheSameDirection(currentShortestPath, position))
          .collect(toSet());
    }

    private boolean canContinueInTheSameDirection(
        List<Node<Position>> currentShortestPath, Position position) {
      List<Position> positions = currentShortestPath.stream().map(Node::getValue).toList();
      // log.debug("positions {}", positions);
      if (positions.size() > 3) {
        List<Position> latest3AndActual =
            new ArrayList<>(positions.subList(positions.size() - 3, positions.size()));
        latest3AndActual.add(position);
        // log.debug("latest3 {}", latest3AndActual);
        Set<Long> xValues = latest3AndActual.stream().map(Position::x).collect(toSet());
        Set<Long> yValues = latest3AndActual.stream().map(Position::y).collect(toSet());
        boolean allTheSame = xValues.size() == 1 || yValues.size() == 1;
        return !allTheSame;
      }
      return true;
    }
  }
}
