package be.bonamis.advent.year2022;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.common.Grid;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
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
        CharGrid grid = new CharGrid(lines.stream().map(String::toCharArray).toArray(char[][]::new));

        var graph = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        grid.consume(graph::addVertex);
        grid.consume(point -> addEdge(graph, point, grid));

        ShortestPathAlgorithm<Point, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        final var source = new Point(0, 0);
        final var sink = new Point(2, 5);

        long dist = (long) dijkstraShortestPath.getPathWeight(source, sink);

        assertThat(dist).isEqualTo(31);
    }

    private void addEdge(DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge> graph, Point point, CharGrid grid) {
        //var value = grid.get(point);
        for (var adjacent : adjacentPoints(point, grid)) {
            var edge = graph.addEdge(adjacent, point);
            //graph.setEdgeWeight(edge, value);
        }
    }

    private Collection<Point> adjacentPoints(Point point, CharGrid grid) {
        var points = new HashSet<Point>();

        addPoint(points, point.x, point.y - 1, grid);
        addPoint(points, point.x, point.y + 1, grid);
        addPoint(points, point.x - 1, point.y, grid);
        addPoint(points, point.x + 1, point.y, grid);

        return points;
    }

    private void addPoint(HashSet<Point> points, int x, int y, CharGrid grid) {
        final var point = new Point(x, y);
        Character character = grid.get(point);
        if (character != null) {
            points.add(point);
        }
    }

    private static boolean canGoToThatCell(int col, char[] maze, char originPointCharacter) {
        char character = maze[col];
        boolean test = (1 >= (character - originPointCharacter));
        return originPointCharacter == 'S' || character == 'E' || test;
    }


    @Test
    @Disabled
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);

    }


    private static long solvePart01(List<String> lines) {
        return lines.size();
    }



}
