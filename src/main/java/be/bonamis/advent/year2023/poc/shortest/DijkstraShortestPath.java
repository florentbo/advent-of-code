package be.bonamis.advent.year2023.poc.shortest;

import be.bonamis.advent.year2023.poc.Node;
import java.util.*;

public class DijkstraShortestPath<T> {

    public List<Node<T>> findShortestPath(Node<T> start, Node<T> end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end nodes must not be null");
        }

        Map<Node<T>, Integer> distanceMap = new HashMap<>();
        Map<Node<T>, Node<T>> parentMap = new HashMap<>();
        PriorityQueue<Node<T>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distanceMap::get));

        distanceMap.put(start, 0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            Node<T> current = priorityQueue.poll();

            if (current == end) {
                // Reconstruct the path
                return reconstructPath(parentMap, start, end);
            }

            for (Map.Entry<Node<T>, Integer> entry : neighbors(current)) {
                Node<T> neighbor = entry.getKey();
                int weight = entry.getValue();
                int newDistance = distanceMap.get(current) + weight;

                if (!distanceMap.containsKey(neighbor) || newDistance < distanceMap.get(neighbor)) {
                    distanceMap.put(neighbor, newDistance);
                    parentMap.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            }
        }

        // No path found
        return Collections.emptyList();
    }

    Iterable<Map.Entry<Node<T>, Integer>> neighbors(Node<T> current) {
        return null;
    }

    private List<Node<T>> reconstructPath(Map<Node<T>, Node<T>> parentMap, Node<T> start, Node<T> end) {
        List<Node<T>> path = new ArrayList<>();
        Node<T> current = end;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        // Example usage
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);

        //node1.addNeighbor(node2, 1);
        //node1.addNeighbor(node3, 4);
        //node2.addNeighbor(node4, 2);
        //node3.addNeighbor(node4, 5);

        DijkstraShortestPath<Integer> pathFinder = new DijkstraShortestPath<>();
        List<Node<Integer>> shortestPath = pathFinder.findShortestPath(node1, node4);

        System.out.println("Shortest Path: " + shortestPath);
    }
}
