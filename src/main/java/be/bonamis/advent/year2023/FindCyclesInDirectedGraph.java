package be.bonamis.advent.year2023;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.awt.*;
import java.util.List;

public class FindCyclesInDirectedGraph {
  public static void main(String[] args) {
    // Create a directed graph
    DefaultDirectedGraph<Point, DefaultEdge> directedGraph = createDirectedGraph();

    // Find cycles in the directed graph
    List<List<Point>> cycles = findCycles(directedGraph);

    // Print the cycles
    System.out.println("Cycles:");
    for (List<Point> cycle : cycles) {
      for (Point point : cycle) {
        System.out.print("(" + point.x + "," + point.y + ") ");
      }
      System.out.println();
    }
  }

  private static DefaultDirectedGraph<Point, DefaultEdge> createDirectedGraph() {
    DefaultDirectedGraph<Point, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

    // Add vertices
    Point a00 = new Point(0, 0);
    Point a01 = new Point(0, 1);
    Point a02 = new Point(0, 2);

    Point a20 = new Point(2, 0);
    Point a21 = new Point(2, 1);
    Point a22 = new Point(2, 2);

    Point a10 = new Point(1, 0);
    Point a12 = new Point(1, 2);

    graph.addVertex(a00);
    graph.addVertex(a01);
    graph.addVertex(a01);

    graph.addVertex(a01);
    graph.addVertex(a01);

    // Add directed edges
    graph.addEdge(a00, a01);

    return graph;
  }

  private static List<List<Point>> findCycles(DefaultDirectedGraph<Point, DefaultEdge> graph) {
    HawickJamesSimpleCycles<Point, DefaultEdge> cycleFinder = new HawickJamesSimpleCycles<>(graph);
    return cycleFinder.findSimpleCycles();
  }
}
