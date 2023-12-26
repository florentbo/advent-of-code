package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.*;

import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day23 extends DaySolver<String> {

  private static final char PATH = '.';
  private static final char FOREST = '#';
  private static final char NORTH_STEEP = '^';
  private static final char SOUTH_STEEP = 'v';
  private static final char EAST_STEEP = '>';
  private static final char WEST_STEEP = '<';
  private static final List<Character> STEEP_DIRECTIONS =
      List.of(NORTH_STEEP, SOUTH_STEEP, EAST_STEEP, WEST_STEEP);

  private final CharGrid grid;

  public Day23(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    Position startPosition = Position.of(1, 0);
    log.debug("startPosition {}", startPosition);
    Position endPosition = Position.of(grid.getWidth() - 2, grid.getHeight() - 1);
    log.debug("endPosition {}", endPosition);
    Rover start = new Rover(EAST, startPosition);
    char dataStart = data(start);
    log.debug("dataStart {}", dataStart);

    List<Position> path = new ArrayList<>();
    Map<Position, Boolean> isVisited = new HashMap<>();
    dfsWalk(startPosition, endPosition, path, isVisited);

    /*Rover end = new Rover(EAST, endPosition);
    char dataEnd = data(end);
    log.debug("dataEnd {}", dataEnd);

    Graph<Point> graph = new Graph<>();
    Map<Point, Node<Point>> nodes = new HashMap<>();
    grid.consume(
        point -> {
          if (isNotForest(point)) {
            Node<Point> node = new Node<>(point);
            nodes.put(point, node);
            graph.addNode(node);
          }
        });

    nodes
        .keySet()
        .forEach(
            point -> {
              for (var adjacent : grid.neighbours(point)) {
                Node<Point> node = nodes.get(point);
                if (isNotForest(adjacent)) {
                  Node<Point> adjacentNode = nodes.get(adjacent);
                  node.addDestination(adjacentNode, 1);
                }
              }
            });

    final var source = startPosition.toPoint();
    final var sink = endPosition.toPoint();
    Node<Point> sourceNode = nodes.get(source);
    Node<Point> sinkNode = nodes.get(sink);*/
    /* Result<Point> result =
        new ForestDijkstraAlgorithm().calculateShortestPathFromSource(sourceNode, sinkNode, true);
    List<Node<Point>> path = result.path();
    log.debug("path  size {}", path.size());
    path.forEach(n -> {
      Point point = n.getName();
      Character c = grid.get(point);
      if (!STEEP_DIRECTIONS.contains(c)) {
        grid.set(point, 'O');
      }
      //log.debug("{}", point);
    });
    grid.rowsAsLines().forEach(s -> log.debug("{}", s));*/
    // graph.printAllPaths(sourceNode, sinkNode);
    return 999L;

    /*ForestLongestPathAlgorithm forestLongestPathAlgorithm = new ForestLongestPathAlgorithm();
    Result<Point> longestResult = forestLongestPathAlgorithm.calculateLongestPathFromSource(sourceNode, sinkNode);
    longestResult.path().forEach(n -> log.debug("{}", n.getName()));

    return longestResult.distance();*/
  }

  private void dfsWalk(
      Position startPosition,
      Position endPosition,
      List<Position> path,
      Map<Position, Boolean> isVisited) {
    if (startPosition.equals(endPosition)) {
      int size = path.size();
      log.debug("path  size {}", size);
      /*if (size > 90 && size < 100) {

        path.forEach(n -> log.debug("{}", n));
      }*/
      return;
    }
    isVisited.put(startPosition, true);
    Set<Position> adjacentNodes =
        allowedPositions(startPosition); // startPosition.getAdjacentNodes().keySet();
    adjacentNodes.forEach(
        node -> {
          if (!isVisited.getOrDefault(node, false)) {
            path.add(node);
            dfsWalk(node, endPosition, path, isVisited);
            path.remove(node);
          }
        });
    isVisited.put(startPosition, false);
  }

  private Set<Position> allowedPositions(Position startPosition) {
    return Arrays.stream(values())
        .map(
            direction -> {
              Rover rover = new Rover(direction, startPosition);
              return rover.move(FORWARD, true);
            })
        .filter(this::isPositionInTheGrid)
        .filter(this::isNotForest)
        //.filter(this::isInGoodDirection)
        .map(Rover::position)
        .collect(Collectors.toSet());
    // is in the grid filter
    // is not a forest filter
    // is slope in good direction
    // return new HashSet<>();
  }

  private boolean isInGoodDirection(Rover rover) {
    char data = data(rover);
    log.debug("data {}", data);
    return switch (rover.direction()) {
      case NORTH -> data == NORTH_STEEP;
      case SOUTH -> data == SOUTH_STEEP;
      case EAST -> data == EAST_STEEP;
      case WEST -> data == WEST_STEEP;
    };
  }

  private boolean isPositionInTheGrid(Rover rover) {
    return grid.isPositionInTheGrid().test(rover.position());
  }

  private boolean isNotForest(Rover rover) {
    char data = grid.get(rover.position());
    return data != FOREST;
  }

  /*  static class ForestLongestPathAlgorithm extends LongestPathAlgorithm<Point> {}

  static class ForestDijkstraAlgorithm extends DijkstraAlgorithm<Point> {
    @Override
    public boolean validate(Node<Point> sourceNode, Node<Point> evaluationNode) {
      return super.validate(sourceNode, evaluationNode);
    }
  }*/

  boolean isNotForest(Point point) {
    char data = grid.get(point);
    return data != FOREST;
  }

  boolean isForest(Point point) {
    char data = grid.get(point);
    return data == FOREST;
  }

  private char data(Rover rover) {
    return grid.get(rover.position());
  }

  @Override
  public long solvePart02() {
    return 9999L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/23/2023_23_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day23 day = new Day23(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
