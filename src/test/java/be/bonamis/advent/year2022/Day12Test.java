package be.bonamis.advent.year2022;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day12Test {

    private static final String CODE_TXT = "2022/12/2022_12_13_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/12/2022_12_input.txt");
        log.info("Day11 part 01 result: {}", solvePart01(lines));
        //log.info("Day11 part 02 result: {}", solvePart02(lines));
    }

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);

        assertThat(solvePart01(lines)).isEqualTo(31);
    }

    private static void addEdge(Graph<Point, DefaultEdge> graph, Point point, CharGrid grid) {
        for (var adjacent : adjacentPoints(point, grid)) {
            graph.addEdge(adjacent, point);
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
            }
        }
    }

    private static boolean canGoToThatCell(char originPointCharacter, char character) {
        int dist = character - originPointCharacter;
        boolean test = dist == 0 || dist == 1;
        boolean showResult = originPointCharacter == 'S' || (originPointCharacter == 'z' && character == 'E') || test;
        return showResult;
    }


    @Test
    @Disabled
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);

    }


    private static long solvePart01(List<String> lines) {
        CharGrid grid = new CharGrid(lines.stream().map(String::toCharArray).toArray(char[][]::new));

        //var graph = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        Graph<Point, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        grid.consume(graph::addVertex);
        grid.consume(point -> addEdge(graph, point, grid));


        final var source = grid.stream().filter(point -> grid.get(point) == 'S').findFirst().orElseThrow();
        final var sink = grid.stream().filter(point -> grid.get(point) == 'E').findFirst().orElseThrow();

        DijkstraShortestPath<Point, DefaultEdge> shortestPath = new DijkstraShortestPath<>(graph);
        List<Point> vertexList = shortestPath.getPath(source, sink).getVertexList();
        for (int i = 1; i < vertexList.size(); i++) {
            System.out.println(grid.get(vertexList.get(i-1)) + " - " + grid.get(vertexList.get(i)));
        }
        return vertexList.size() - 1;
    }


}
