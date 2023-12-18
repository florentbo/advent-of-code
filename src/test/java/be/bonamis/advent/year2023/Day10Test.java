package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
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
    CharGrid grid = new CharGrid(Arrays.asList(squareLoopText.split("\n")));

    int result = solvePart01(grid);
    assertThat(result).isEqualTo(4);
  }

  @Test
  void solvePart01Example2() {
    String text = """
  ..F7.
  .FJ|.
  SJ.L7
  |F--J
  LJ...
  """;
    CharGrid grid = new CharGrid(Arrays.asList(text.split("\n")));

    int result = solvePart01(grid);
    assertThat(result).isEqualTo(8);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/10/2023_10_input.txt");
    CharGrid grid = new CharGrid(Arrays.asList(content.split("\n")));

    int result = solvePart01(grid);
    log.info("solution part 1: {}", result);
  }

  private static int solvePart01(CharGrid grid) {
    final var source = getPoint(grid, 'S');
    log.debug("source {}", source);
    List<Point> neighbours = grid.neighbours(source, false);
    Stream<Point> sinks = neighbours.stream().filter(p -> grid.get(p) != '.');

    List<Integer> results = sinks.map(sink -> result(grid, sink, source)).toList();
    log.debug("results {}", results);
    return results.get(0);
  }

  private static int result(CharGrid grid, Point sink, Point source) {
    log.debug("sink {} value {}", sink, grid.get(sink));

    Graph<Point, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    grid.consume(graph::addVertex);
    grid.consume(point -> addEdge(graph, point, grid, source, sink));

    List<Point> vertexList = vertexList(sink, source, graph);
    int result = vertexList.size() / 2;
    log.debug("solution size: {}", result);
    return result;
  }

  private static List<Point> vertexList(Point sink, Point source, Graph<Point, DefaultEdge> graph) {
    DijkstraShortestPath<Point, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
    GraphPath<Point, DefaultEdge> path = shortestPath.getPath(source, sink);
    return path.getVertexList();
  }

  static void addEdge(
      Graph<Point, DefaultEdge> graph, Point point, CharGrid grid, Point source, Point sink) {
    if (isNotDot(point, grid)) {
      Set<Direction> allowedDirections = allowedDirections(grid.get(point));
      Direction[] directions = allowedDirections.toArray(new Direction[0]);
      for (Point neighbour : allowedPoints(point, grid, directions)) {
        boolean start = point.equals(source) && neighbour.equals(sink);
        if (!start) {
          graph.addEdge(point, neighbour);
        }
      }
    }
  }

  static Set<Point> allowedPoints(Point point, CharGrid grid, Direction... directions) {
    return Arrays.stream(directions)
        .map(
            direction -> {
              Position position = position(point, direction.verticalInverse());
              // log.debug("position {} -> {}", point, position);
              return new Point((int) position.x(), (int) position.y());
            })
        .filter(grid.isInTheGrid())
        .filter(p -> isNotDot(p, grid))
        .collect(Collectors.toSet());
  }

  /*
    | is a vertical pipe connecting north and south.
  - is a horizontal pipe connecting east and west.
  L is a 90-degree bend connecting north and east.
  J is a 90-degree bend connecting north and west.
  7 is a 90-degree bend connecting south and west.
  F is a 90-degree bend connecting south and east.
     */

  static Set<Direction> allowedDirections(Character value) {
    // log.debug("checking allowed directions for {}", value);
    return switch (value) {
      case '|' -> Set.of(NORTH, SOUTH);
      case '-' -> Set.of(EAST, WEST);
      case 'L' -> Set.of(NORTH, EAST);
      case 'J' -> Set.of(NORTH, WEST);
      case '7' -> Set.of(SOUTH, WEST);
      case 'F' -> Set.of(SOUTH, EAST);
      case 'S' -> Arrays.stream(values()).collect(Collectors.toSet());
      default -> throw new NoSuchElementException();
    };
  }

  private static Position position(Point point, Direction direction) {
    return new Rover(direction, new Position(point.x, point.y)).move(FORWARD).position();
  }

  @Test
  void allowedPoints() {
    CharGrid grid = new CharGrid(Arrays.asList(squareLoopText.split("\n")));
    assertThat(allowedPoints(new Point(1, 2), grid, NORTH)).containsExactly(new Point(1, 1));
    assertThat(allowedPoints(new Point(1, 2), grid, SOUTH)).containsExactly(new Point(1, 3));
    assertThat(allowedPoints(new Point(1, 2), grid, WEST)).isEmpty();
    assertThat(allowedPoints(new Point(1, 2), grid, EAST)).isEmpty();

    assertThat(allowedPoints(new Point(1, 1), grid, EAST)).containsExactly(new Point(2, 1));
    assertThat(allowedPoints(new Point(3, 1), grid, WEST)).containsExactly(new Point(2, 1));

    assertThat(allowedPoints(new Point(1, 1), grid, EAST, SOUTH))
        .containsExactlyInAnyOrder(new Point(1, 2), new Point(2, 1));
  }

  public static boolean isNotDot(Point point, CharGrid grid) {
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
