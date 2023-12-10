package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.*;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.*;

@Slf4j
class Day10Test {
  private Day10 day10;

  @Test
  void solvePart01() {
    String text = """
.....
.S-7.
.|.|.
.L-J.
.....
""";
    day10 = new Day10(Arrays.asList(text.split("\n")));
    List<String> lines = Arrays.asList(text.split("\n"));
    CharGrid grid = new CharGrid(lines.stream().map(String::toCharArray).toArray(char[][]::new));
    final var source = getPoint(grid, 'S');
    log.debug("source {}", source);
    List<Point> neighbours = grid.neighbours(source, false);
    assertThat(neighbours).hasSize(4);
    neighbours.forEach(p -> log.debug("neighbour {} value {}", p, grid.get(p)));
    final var sink = getPoint(grid, 'J');
    //Point sink = neighbours.stream().filter(p -> grid.get(p) == 'J').findFirst().orElseThrow();
    log.debug("sink {}", sink);

    Graph<Point, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    grid.consume(graph::addVertex);
    grid.consume(point -> addEdge(graph, point, grid));

    DijkstraShortestPath<Point, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
    GraphPath<Point, DefaultEdge> path = shortestPath.getPath(source, sink);
    List<Point> vertexList = path.getVertexList();
    vertexList.forEach(p -> log.debug("vertex {}", p));

    // final var sink = getPoint(grid, 'E');

    // assertThat(day10.solvePart01()).isEqualTo(4);
  }

  private void addEdge(Graph<Point, DefaultEdge> graph, Point point, CharGrid grid) {
    for (Point neighbour : grid.neighbours(point, false)) {
      graph.addEdge(point, neighbour);
    }
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
    day10 = new Day10(Arrays.asList(text.split("\n")));
    assertThat(day10.solvePart02()).isEqualTo(4);
  }
}
