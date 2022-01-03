package be.bonamis.advent.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class Graph {
    Set<String> vertices;

    public Map<String, LinkedList<Node>> adjacencyList;

    public Graph(Set<String> vertices) {
        this.vertices = vertices;
        adjacencyList = new HashMap<>();
        for (String vertex : vertices) {
            adjacencyList.put(vertex, new LinkedList<>());
        }
    }

    public void addEdge(String source, String destination) {
        Node node = new Node(source, destination);
        final var nodes = adjacencyList.get(source);
        nodes.add(node);
        adjacencyList.put(source, nodes);
    }

    public void addEdge(Node node) {
        String source = node.source;
        final var nodes = adjacencyList.get(source);
        nodes.add(node);
        adjacencyList.put(source, nodes);
    }

    @ToString
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Node {
        private final String source;
        private final String destination;

        public static Node parse(String input) {
            final var split = input.split("-");
            return new Node(split[0], split[1]);
        }
    }
}
