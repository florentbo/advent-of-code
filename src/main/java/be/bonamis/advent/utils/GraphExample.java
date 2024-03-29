package be.bonamis.advent.utils;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class GraphExample {

  public static void main(String[] args) {
    Graph<String, DefaultEdge> directedGraph = aDirectedGraph();

    // computes all the strongly connected components of the directed graph
    StrongConnectivityAlgorithm<String, DefaultEdge> scAlg =
        new KosarajuStrongConnectivityInspector<>(directedGraph);
    List<Graph<String, DefaultEdge>> stronglyConnectedSubgraphs =
        scAlg.getStronglyConnectedComponents();

    // prints the strongly connected components
    System.out.println("Strongly connected components:");
    for (Graph<String, DefaultEdge> stronglyConnectedSubgraph : stronglyConnectedSubgraphs) {
      System.out.println(stronglyConnectedSubgraph);
    }
    System.out.println();

    // Prints the shortest path from vertex i to vertex c. This certainly
    // exists for our particular directed graph.
    System.out.println("Shortest path from i to c:");
    DijkstraShortestPath<String, DefaultEdge> dijkstraAlg =
        new DijkstraShortestPath<>(directedGraph);
    ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths("i");
    System.out.println(iPaths.getPath("c") + "\n");

    // Prints the shortest path from vertex c to vertex i. This path does
    // NOT exist for our particular directed graph. Hence the path is
    // empty and the result must be null.
    System.out.println("Shortest path from c to i:");
    ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> cPaths = dijkstraAlg.getPaths("c");
    System.out.println(cPaths.getPath("i"));
  }

  private static Graph<String, DefaultEdge> aDirectedGraph() {
    Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    directedGraph.addVertex("a");
    directedGraph.addVertex("b");
    directedGraph.addVertex("c");
    directedGraph.addVertex("d");
    directedGraph.addVertex("e");
    directedGraph.addVertex("f");
    directedGraph.addVertex("g");
    directedGraph.addVertex("h");
    directedGraph.addVertex("i");
    directedGraph.addEdge("a", "b");
    directedGraph.addEdge("b", "d");
    directedGraph.addEdge("d", "c");
    directedGraph.addEdge("c", "a");
    directedGraph.addEdge("e", "d");
    directedGraph.addEdge("e", "f");
    directedGraph.addEdge("f", "g");
    directedGraph.addEdge("g", "e");
    directedGraph.addEdge("h", "e");
    directedGraph.addEdge("i", "h");
    return directedGraph;
  }
}
