package be.bonamis.advent.year2022;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day12Test {

    private static final String CODE_TXT = "2022/12/2022_12_13_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/12/2022_12_input.txt");
        log.info("Day12 part 01 result: {}", solvePart01(lines));
        log.info("Day12 part 02 result: {}", solvePart02(lines));
    }

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(solvePart01(lines)).isEqualTo(31);
    }

    private static long solvePart01(List<String> lines) {
        CharGrid grid = new CharGrid(lines.stream().map(String::toCharArray).toArray(char[][]::new));
        final var source = getPoint(grid, 'S');
        final var sink = getPoint(grid, 'E');
        return shortestPath(grid, source, sink);
    }

    @Test
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(solvePart02(lines)).isEqualTo(29);
    }

    private static long solvePart02(List<String> lines) {
        CharGrid grid = new CharGrid(lines.stream().map(String::toCharArray).toArray(char[][]::new));
        final var sink = getPoint(grid, 'E');
        return filter(grid, 'a').map(point -> shortestPath(grid, point, sink)).min(Integer::compareTo).orElseThrow();
    }

    private static int shortestPath(CharGrid grid, Point source, Point sink) {
        Graph<Point, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        grid.consume(graph::addVertex);
        grid.consume(point -> addEdge(graph, point, grid));

        DijkstraShortestPath<Point, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Point, DefaultEdge> path = shortestPath.getPath(source, sink);
        /*for (int i = 1; i < vertexList.size(); i++) {
            System.out.println(grid.get(vertexList.get(i-1)) + " - " + grid.get(vertexList.get(i)));
        }*/
        return path!= null ? path.getVertexList().size() - 1: Integer.MAX_VALUE;
    }

    private static void addEdge(Graph<Point, DefaultEdge> graph, Point point, CharGrid grid) {
        for (var adjacent : adjacentPoints(point, grid)) {
            graph.addEdge(point, adjacent);
        }
    }

    private static Collection<Point> adjacentPoints(Point point, CharGrid grid) {
        var points = new HashSet<Point>();
        addPoint(points, point.x, point.y - 1, grid, point);
        addPoint(points, point.x, point.y + 1, grid, point);
        addPoint(points, point.x - 1, point.y, grid, point);
        addPoint(points, point.x + 1, point.y, grid, point);
        return points;
    }

    private static void addPoint(Set<Point> points, int x, int y, CharGrid grid, Point origin) {
        final var point = new Point(x, y);
        char originPointCharacter = grid.get(origin);
        Character character = grid.get(point);
        if (character != null) {
            if (canGoToThatCell(originPointCharacter, character)) {
                points.add(point);
                //System.out.println("added point: " + point);
            }
        }
    }

    private static boolean canGoToThatCell(char originPointCharacter, char character) {
        return (character=='E'? 'z' : character) - (originPointCharacter == 'S' ? 'a' : originPointCharacter) <= 1;
    }



    private static Point getPoint(CharGrid grid, char c) {
        return filter(grid, c).findFirst().orElseThrow();
    }

    private static Stream<Point> filter(CharGrid grid, char c) {
        return grid.stream().filter(point -> grid.get(point) == c);
    }
}
