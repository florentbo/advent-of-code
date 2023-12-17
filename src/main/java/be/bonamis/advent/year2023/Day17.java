package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Grid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import be.bonamis.advent.year2023.poc.DijkstraAlgorithm;
import be.bonamis.advent.year2023.poc.Graph;
import be.bonamis.advent.year2023.poc.Node;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

@Slf4j
@Getter
public class Day17 extends DaySolver<String> {

  private final int[][] data;
  private final Grid grid;

  public Day17(List<String> puzzle) {
    super(puzzle);
    this.data =
        puzzle.parallelStream()
            .map(l -> l.trim().split(""))
            .map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
            .toArray(int[][]::new);
    grid = new Grid(data);
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

  public long solveShortestPathDijkstra(boolean extraValidation) {
    Graph graph = new Graph();
    Map<Point, Node<Point>> nodes = new HashMap<>();
    grid.consume(
        point -> {
          Node<Point> node = new Node<>(point);
          nodes.put(point, node);
          graph.addNode(node);
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
    DijkstraAlgorithm.Result<Point> result =
        new PointDijkstraAlgorithm()
            .calculateShortestPathFromSource(sourceNode, sinkNode, extraValidation);

    log.debug("result {}", result);
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

  static class PointDijkstraAlgorithm extends DijkstraAlgorithm<Point> {
    @Override
    public boolean validate(Node<Point> sourceNode, Node<Point> evaluationNode) {
      List<Node<Point>> sourceNodePath = sourceNode.getShortestPath();
      List<Node<Point>> evaluationNodePath = evaluationNode.getShortestPath();
      if (sourceNodePath.size() > 2) {
        List<Node<Point>> last3sourceNodePath =
            sourceNodePath.subList(sourceNodePath.size() - 3, sourceNodePath.size());
        List<Point> last3Points = last3sourceNodePath.stream().map(Node::getName).toList();
        Point evaluationPoint = evaluationNode.getName();
        boolean xCoordinatesAreNotAllTheSame =
            last3Points.stream().map(p -> p.x).anyMatch(p -> p != evaluationPoint.x);
        boolean yCoordinatesAreNotAllTheSame =
            last3Points.stream().map(p -> p.y).anyMatch(p -> p != evaluationPoint.y);
        /*List<Integer> xCoordinates = last3Points.stream().map(p -> p.x).toList();
        List<Integer> yCoordinates = last3Points.stream().map(p -> p.y).toList();
        log.debug("last3sourceNodePath: {}", last3sourceNodePath);*/
        return xCoordinatesAreNotAllTheSame && yCoordinatesAreNotAllTheSame;
      }
      return true;
    }
  }
}
