package be.bonamis.advent.year2024;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.graph.*;

import java.util.List;
import java.util.Set;

public class InterconnectedNodes {
    public static void main(String[] args) {
        // Create an undirected graph
        SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Add vertices and edges based on your pairs
        String[] edges = {"yn-aq", "qp-ub", "cg-tb", "vc-aq", "tb-ka", "wh-tc", "aq-cg", "yn-cg"};
        for (String edge : edges) {
            String[] vertices = edge.split("-");
            graph.addVertex(vertices[0]);
            graph.addVertex(vertices[1]);
            graph.addEdge(vertices[0], vertices[1]); // This creates a bidirectional edge
        }

        // Find connected components (since the graph is undirected)
        ConnectivityInspector<String, DefaultEdge> inspector = new ConnectivityInspector<>(graph);

        List<Set<String>> connectedComponents = inspector.connectedSets();

        // Print out components with three or more interconnected nodes
        for (Set<String> component : connectedComponents) {
            if (component.size() >= 3) {
                System.out.println("Interconnected nodes: " + component);
            }
        }
    }
}
