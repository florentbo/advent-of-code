package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.common.Grid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.year2023.poc.DijkstraAlgorithm;
import be.bonamis.advent.year2023.poc.DijkstraAlgorithm.Result;
import be.bonamis.advent.year2023.poc.Node;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.values;
import static java.util.stream.Collectors.*;

@Slf4j
@Getter
public class Day17 extends DaySolver<String> {

  private final int[][] data;
  private final Grid grid;
  private final CharGrid charGrid;

  public Day17(List<String> puzzle) {
    super(puzzle);
    this.data =
        puzzle.parallelStream()
            .map(l -> l.trim().split(""))
            .map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
            .toArray(int[][]::new);
    grid = new Grid(data);
    charGrid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    return shortestPath(grid);
  }

  private long shortestPath(Grid grid) {
    var graph =
        new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    grid.consume(graph::addVertex);
    grid.consume(point -> addEdge(graph, point, grid));

    ShortestPathAlgorithm<Point, DefaultWeightedEdge> dijkstraShortestPath =
        new DijkstraShortestPath<>(graph);

    final var source = new Point(0, 0);
    final var sink = new Point(grid.getHeight() - 1, grid.getWidth() - 1);
    return (long) dijkstraShortestPath.getPathWeight(source, sink);
  }

  private void addEdge(
      DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge> graph, Point point, Grid grid) {
    var value = grid.get(point);
    for (var adjacent : adjacentPoints(point, grid)) {
      var edge = graph.addEdge(adjacent, point);
      graph.setEdgeWeight(edge, value);
    }
  }

  private Collection<Point> adjacentPoints(Point point, Grid grid) {
    var points = new HashSet<Point>();

    addPoint(points, point.x, point.y - 1, grid);
    addPoint(points, point.x, point.y + 1, grid);
    addPoint(points, point.x - 1, point.y, grid);
    addPoint(points, point.x + 1, point.y, grid);

    return points;
  }

  private void addPoint(HashSet<Point> points, int x, int y, Grid grid) {
    final var point = new Point(x, y);
    Integer value = grid.get(point);
    if (value != null) {
      points.add(point);
    }
  }

  public long solveCustomShortest() {
    Map<Position, Node<Position>> nodes = new HashMap<>();
    charGrid.consume(
        point -> {
          Position position = Position.of(point);
          Node<Position> node = new Node<>(position);
          nodes.put(position, node);
        });

    Position startPosition = Position.of(0, 0);
    log.info("startPosition {}", startPosition);
    Position endPosition = Position.of(grid.getWidth() - 1, grid.getHeight() - 1);
    log.info("endPosition {}", endPosition);
    Node<Position> sourceNode = nodes.get(startPosition);
    Node<Position> sinkNode = nodes.get(endPosition);

    Result<Position> result =
        new PositionDijkstraAlgorithm(charGrid, nodes)
            .calculateShortestPathFromSource(sourceNode, sinkNode, false);

    return result.distance();
  }

  public long solveShortestPathDijkstra(boolean extraValidation) {
    // Graph<Point> graph = new Graph<>();
    Map<Point, Node<Point>> nodes = new HashMap<>();
    grid.consume(
        point -> {
          Node<Point> node = new Node<>(point);
          nodes.put(point, node);
          // graph.addNode(node);
        });
    grid.consume(
        point -> {
          Integer value = grid.get(point);
          Node<Point> node = nodes.get(point);
          for (var adjacent : adjacentPoints(point, grid)) {
            Node<Point> adjacentNode = nodes.get(adjacent);
            node.addDestination(adjacentNode, value);
            // log.debug("Adding edge {} to {}", value, adjacentNode);
            // log.debug("node {} adj {}", adjacentNode, adjacentNode.getAdjacentNodes());
            // log.debug("node adja");
          }
        });

    final var source = new Point(0, 0);
    final var sink = new Point(grid.getHeight() - 1, grid.getWidth() - 1);
    Node<Point> sourceNode = nodes.get(source);
    Node<Point> sinkNode = nodes.get(sink);
    Result<Point> result =
        new PointDijkstraAlgorithm()
            .calculateShortestPathFromSource(sourceNode, sinkNode, extraValidation);

    result.path().forEach(n -> log.debug("{}", n.getValue()));
    return result.distance();
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

  static class PointDijkstraAlgorithm extends DijkstraAlgorithm<Point> {}

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
          // .filter(this::isNotForest)
          .filter(rover -> canContinueInTheSameDirection(rover, currentShortestPath))
          .map(Rover::position)
          .collect(toSet());
    }

    private boolean canContinueInTheSameDirection(
        Rover rover, List<Node<Position>> currentShortestPath) {
      Position position = rover.position();
      List<Position> positions = currentShortestPath.stream().map(Node::getValue).toList();
      log.debug("positions {}", positions);
      if (positions.size() > 3) {
        List<Position> latest3AndActual =
            new ArrayList<>(positions.subList(positions.size() - 3, positions.size()));
        latest3AndActual.add(position);
        log.debug("latest3 {}", latest3AndActual);
        Set<Long> xValues = latest3AndActual.stream().map(Position::x).collect(toSet());
        Set<Long> yValues = latest3AndActual.stream().map(Position::y).collect(toSet());
        boolean allTheSame = xValues.size() == 1 || yValues.size() == 1;
        log.debug("allTheSame {}", allTheSame);
        return !allTheSame;
      }
      return true;
    }
  }
}
