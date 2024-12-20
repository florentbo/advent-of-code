package be.bonamis.advent.year2024;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.*;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.SimpleGraph;

@Getter
@Slf4j
public class Day16 extends TextDaySolver {

  private final CharGrid grid;

  public Day16(InputStream inputStream) {
    super(inputStream);
    this.grid = new CharGrid(this.puzzle);
  }

  public Day16(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(this.puzzle);
  }

  // Add this helper method to calculate weights:
  private double calculateWeight(char source, char target) {
    // Implement your weight calculation logic here
    // For example, you could return different weights based on the characters
    return 1.0; // Default weight
  }

  @Override
  public long solvePart01() {
    char[][] data = this.grid.getData();

    Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    Map<String, Integer[]> nodeCoordinates = new HashMap<>();

    int rows = data.length;
    int cols = data[0].length;

    // Convert grid to graph
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (data[i][j] != '#') {
          String nodeName = i + "," + j;
          graph.addVertex(nodeName);
          nodeCoordinates.put(nodeName, new Integer[] {i, j});

          if (i > 0 && data[i - 1][j] != '#') {
            String targetVertex = (i - 1) + "," + j;
            log.debug("add edge from {} to {}", nodeName, targetVertex);
            DefaultWeightedEdge edge = graph.addEdge(nodeName, targetVertex);
            if (edge != null) {
              double weight = calculateWeight(data[i][j], data[i - 1][j]);
              graph.setEdgeWeight(edge, weight);
            }
          }
          if (j > 0 && data[i][j - 1] != '#') {
            String targetVertex = i + "," + (j - 1);
            log.debug("add edge from {} to {}", nodeName, targetVertex);
            DefaultWeightedEdge edge = graph.addEdge(nodeName, targetVertex);
            if (edge != null) {
              double weight = calculateWeight(data[i][j], data[i][j - 1]);
              graph.setEdgeWeight(edge, weight);
            }
          }
        }
      }
    }

    // Find start and end nodes
    String startNode = null;
    String endNode = null;
    for (Map.Entry<String, Integer[]> entry : nodeCoordinates.entrySet()) {
      Integer[] coords = entry.getValue();
      if (data[coords[0]][coords[1]] == 'S') {
        startNode = entry.getKey();
      } else if (data[coords[0]][coords[1]] == 'E') {
        endNode = entry.getKey();
      }
    }

    // Compute shortest path using Dijkstra's algorithm
    if (startNode != null && endNode != null) {
      DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAlg =
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

    return 0;
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
