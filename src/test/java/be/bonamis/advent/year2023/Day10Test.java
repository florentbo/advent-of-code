package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import lombok.extern.slf4j.*;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.*;

@Slf4j
class Day10Test {
  private final String squareLoopText = """
.....
.S-7.
.|.|.
.L-J.
.....
""";

  @Test
  void solvePart01() {
    CharGrid grid = grid(squareLoopText);
    final var source = getPoint(grid, 'S');
    log.debug("source {}", source);
    List<Point> neighbours = grid.neighbours(source, false);
    assertThat(neighbours).hasSize(4);
    final var sink = neighbours.stream().filter(p -> grid.get(p) != '.').findFirst().orElseThrow();
    log.debug("sink {}", sink);

    Graph<Point, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    grid.consume(graph::addVertex);
    grid.consume(point -> addEdge(graph, point, grid, source, sink));

    DijkstraShortestPath<Point, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
    GraphPath<Point, DefaultEdge> path = shortestPath.getPath(source, sink);
    List<Point> vertexList = path.getVertexList();
    int result = vertexList.size() / 2;
    log.debug("solution size: {}", result);
    vertexList.forEach(p -> log.debug("vertex {}", p));
    assertThat(result).isEqualTo(4);
  }

  private CharGrid grid(String text) {
    return new CharGrid(
        Arrays.stream(text.split("\n")).map(String::toCharArray).toArray(char[][]::new));
  }

  private void addEdge(
      Graph<Point, DefaultEdge> graph, Point point, CharGrid grid, Point source, Point sink) {
    for (Point neighbour : grid.neighbours(point, false)) {
      boolean start = point.equals(source) && neighbour.equals(sink);
      if (!start && isNotDot(neighbour, grid)) {
        graph.addEdge(point, neighbour);
      }
    }
  }

  private Set<Point> allowedDirections(Point point, CharGrid grid, Direction... directions) {
    return Arrays.stream(directions)
        .map(
            direction -> {
              Position position = position(point, direction.verticalInverse());
              return new Point(position.x(), position.y());
            })
        .filter(p -> isNotDot(p, grid))
        .filter(grid.isInTheGrid())
        .collect(Collectors.toSet());
  }

  private Position position(Point point, Direction direction) {
    return new Rover(direction, new Position(point.x, point.y)).move(FORWARD).position();
  }

  @Test
  void allowedDirections() {
    CharGrid grid = grid(squareLoopText);
    assertThat(allowedDirections(new Point(1, 2), grid, NORTH)).containsExactly(new Point(1, 1));
    assertThat(allowedDirections(new Point(1, 2), grid, SOUTH)).containsExactly(new Point(1, 3));
    assertThat(allowedDirections(new Point(1, 2), grid, WEST)).isEmpty();
    assertThat(allowedDirections(new Point(1, 2), grid, EAST)).isEmpty();

    assertThat(allowedDirections(new Point(1, 1), grid, EAST)).containsExactly(new Point(2, 1));
    assertThat(allowedDirections(new Point(3, 1), grid, WEST)).containsExactly(new Point(2, 1));

    assertThat(allowedDirections(new Point(1, 1), grid, EAST, SOUTH))
        .containsExactlyInAnyOrder(new Point(1, 2), new Point(2, 1));
  }

  private boolean isNotDot(Point point, CharGrid grid) {
    return grid.get(point) != '.';
  }

  private static Point getPoint(CharGrid grid, char c) {
    return filter(grid, c).findFirst().orElseThrow();
  }

  private static Stream<Point> filter(CharGrid grid, char c) {
    return grid.stream().filter(point -> grid.get(point) == c);
  }

  @Test
  void solvePart02() {
    String text = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
""";
    Day10 day10 = new Day10(Arrays.asList(text.split("\n")));
    assertThat(day10.solvePart02()).isEqualTo(4);
  }
}
