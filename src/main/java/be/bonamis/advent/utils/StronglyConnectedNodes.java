package be.bonamis.advent.utils;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.graph.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class StronglyConnectedNodes {
  public static void main(String[] args) {

    var graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

    String[] edges = {
      "yn-aq", "qp-ub", "cg-tb", "vc-aq",
      "tb-ka", "wh-tc", "aq-cg", "yn-cg"
    };

    var vertexes =
        Arrays.stream(edges).flatMap(s -> Arrays.stream(s.split("-"))).collect(Collectors.toSet());
    log.debug("vertices: {}", vertexes);
    vertexes.forEach(graph::addVertex);

    for (String edge : edges) {
      String[] parts = edge.split("-");
      graph.addVertex(parts[0]);
      graph.addVertex(parts[1]);
      graph.addEdge(parts[0], parts[1]);
    }

    // Find sets of three interconnected computers
    List<String> vertices = new ArrayList<>(graph.vertexSet());
    int count = 0;

    for (int i = 0; i < vertices.size(); i++) {
      for (int j = i + 1; j < vertices.size(); j++) {
        for (int k = j + 1; k < vertices.size(); k++) {
          String v1 = vertices.get(i);
          String v2 = vertices.get(j);
          String v3 = vertices.get(k);
          if (graph.containsEdge(v1, v2)
              && graph.containsEdge(v2, v3)
              && graph.containsEdge(v1, v3)) {
            //if (v1.startsWith("t") || v2.startsWith("t") || v3.startsWith("t")) {
              count++;
            //}
          }
        }
      }
    }

    System.out.println("Count of valid triples: " + count);

    /*List<DirectedSubgraph<String, DefaultEdge>> stronglyConnectedSubgraphs = scAlg.stronglyConnectedSubgraphs();
    System.out.println("stronglyConnectedSubgraphs = " + stronglyConnectedSubgraphs);
    List<String> stronglyConnectedVertices = new ArrayList<>(stronglyConnectedSubgraphs.get(3).vertexSet());
    System.out.println("stronglyConnectedVertices = " + stronglyConnectedVertices);*/
  }
}
