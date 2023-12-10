package be.bonamis.advent.year2023;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.awt.*;
import java.util.List;

public class FindCyclesInDirectedGrid {
    public static void main(String[] args) {
        // Example grid
        char[][] grid = {
                {'.', '.', '.', '.', '.'},
                {'.', 'S', '-', '7', '.'},
                {'.', '|', '.', '|', '.'},
                {'.', 'L', '-', 'J', '.'},
                {'.', '.', '.', '.', '.'}
        };

        // Create a directed graph based on the grid
        DefaultDirectedGraph<Point, DefaultEdge> directedGraph = createDirectedGraphFromGrid(grid);

        // Find cycles starting from the 'S' character
        List<List<Point>> cycles = findCycles(directedGraph, new Point(1, 1)); // Coordinates of 'S'

        // Print the cycles
        System.out.println("Cycles:");
        for (List<Point> cycle : cycles) {
            for (Point point : cycle) {
                System.out.print("(" + point.x + "," + point.y + ") ");
            }
            System.out.println();
        }
    }

    private static DefaultDirectedGraph<Point, DefaultEdge> createDirectedGraphFromGrid(char[][] grid) {
        DefaultDirectedGraph<Point, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] != '.') {
                    Point point = new Point(x, y);
                    graph.addVertex(point);

                    // Add directed edges for neighboring cells
                    addDirectedEdge(graph, point, new Point(x + 1, y));
                    addDirectedEdge(graph, point, new Point(x - 1, y));
                    addDirectedEdge(graph, point, new Point(x, y + 1));
                    addDirectedEdge(graph, point, new Point(x, y - 1));
                }
            }
        }

        return graph;
    }

    private static void addDirectedEdge(DefaultDirectedGraph<Point, DefaultEdge> graph, Point source, Point target) {
        if (graph.containsVertex(source) && graph.containsVertex(target)) {
            graph.addEdge(source, target);
        }
    }

    private static List<List<Point>> findCycles(DefaultDirectedGraph<Point, DefaultEdge> graph, Point startVertex) {
        HawickJamesSimpleCycles<Point, DefaultEdge> cycleFinder = new HawickJamesSimpleCycles<>(graph);
        return cycleFinder.findSimpleCycles();
    }
}
