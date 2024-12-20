package be.bonamis.advent.utils;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;
import java.util.Map;

public class GridShortestPath {
    public static void main(String[] args) {
        char[][] grid = {
                {'S', '.', '.', '#'},
                {'.', '#', '.', '.'},
                {'.', '.', '.', 'E'}
        };

        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Map<String, Integer[]> nodeCoordinates = new HashMap<>();

        int rows = grid.length;
        int cols = grid[0].length;

        // Convert grid to graph
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != '#') {
                    String nodeName = i + "," + j;
                    graph.addVertex(nodeName);
                    nodeCoordinates.put(nodeName, new Integer[]{i, j});

                    // Connect to adjacent nodes
                    if (i > 0 && grid[i - 1][j] != '#') {
                        Graphs.addEdgeWithVertices(graph, nodeName, (i - 1) + "," + j);
                    }
                    if (j > 0 && grid[i][j - 1] != '#') {
                        Graphs.addEdgeWithVertices(graph, nodeName, i + "," + (j - 1));
                    }
                }
            }
        }

        // Find start and end nodes
        String startNode = null;
        String endNode = null;
        for (Map.Entry<String, Integer[]> entry : nodeCoordinates.entrySet()) {
            Integer[] coords = entry.getValue();
            if (grid[coords[0]][coords[1]] == 'S') {
                startNode = entry.getKey();
            } else if (grid[coords[0]][coords[1]] == 'E') {
                endNode = entry.getKey();
            }
        }

        // Compute shortest path using Dijkstra's algorithm
        if (startNode != null && endNode != null) {
            DijkstraShortestPath<String, DefaultEdge> dijkstraAlg =
                    new DijkstraShortestPath<>(graph);
            var path = dijkstraAlg.getPath(startNode, endNode);

            if (path != null) {
                System.out.println("Shortest path from S to E: " + path.getVertexList());
                System.out.println("Total cost: " + path.getWeight());
            } else {
                System.out.println("No path found from S to E.");
            }
        } else {
            System.out.println("Start or end node not found.");
        }
    }
}
