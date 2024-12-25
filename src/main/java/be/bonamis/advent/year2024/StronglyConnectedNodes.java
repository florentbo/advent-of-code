package be.bonamis.advent.year2024;
import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.graph.*;

import java.util.List;
import java.util.Set;

public class StronglyConnectedNodes {
    public static void main(String[] args) {
        // Create a directed graph
        DirectedWeightedMultigraph<String, DefaultEdge> graph =
                new DirectedWeightedMultigraph<>(DefaultEdge.class);

        // Define input pairs
        String[] edges = {
                "yn-aq", "qp-ub", "cg-tb", "vc-aq",
                "tb-ka", "wh-tc", "aq-cg", "yn-cg"
        };

        // Add vertices and directed edges based on your pairs
        for (String edge : edges) {
            String[] vertices = edge.split("-");
            String source = vertices[0];
            String target = vertices[1];
            System.out.println("Adding edge: " + source + " -> " + target);

            // Add vertices (if they don't exist already)
            graph.addVertex(source);
            graph.addVertex(target);

            // Add directed edge from source to target
            graph.addEdge(source, target);
        }

        // Find strongly connected components using Kosaraju's algorithm
        KosarajuStrongConnectivityInspector<String, DefaultEdge> inspector =
                new KosarajuStrongConnectivityInspector<>(graph);

        List<Set<String>> stronglyConnectedComponents = inspector.stronglyConnectedSets();

        // Print out all strongly connected components
        System.out.println("All strongly connected components:");
        for (Set<String> component : stronglyConnectedComponents) {
            System.out.println("Strongly interconnected nodes: " + component);
        }

        // Additional output to verify connections manually
        System.out.println("\nGraph vertices: " + graph.vertexSet());
        System.out.println("Graph edges: " + graph.edgeSet());
    }
}
