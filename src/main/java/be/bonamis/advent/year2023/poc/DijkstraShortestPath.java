package be.bonamis.advent.year2023.poc;

import java.util.*;

public class DijkstraShortestPath<Position> {

  public List<Node<Position>> findShortestPath(Node<Position> start, Node<Position> end) {


    Map<Node<Position>, Integer> distanceMap = new HashMap<>();
    Map<Node<Position>, Node<Position>> parentMap = new HashMap<>();
    PriorityQueue<Node<Position>> priorityQueue =
        new PriorityQueue<>(Comparator.comparingInt(distanceMap::get));

    distanceMap.put(start, 0);
    priorityQueue.add(start);

    while (!priorityQueue.isEmpty()) {
      Node<Position> current = priorityQueue.poll();

      if (current == end) {
        // Reconstruct the path
        return reconstructPath(parentMap, start, end);
      }

      for (Map.Entry<Node<Position>, Integer> entry : neighbors(current)) {
        Node<Position> neighbor = entry.getKey();
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

  Iterable<Map.Entry<Node<Position>, Integer>> neighbors(Node<Position> current) {
    return null;
  }

  private List<Node<Position>> reconstructPath(
      Map<Node<Position>, Node<Position>> parentMap, Node<Position> start, Node<Position> end) {
    List<Node<Position>> path = new ArrayList<>();
    Node<Position> current = end;

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

    // node1.addNeighbor(node2, 1);
    // node1.addNeighbor(node3, 4);
    // node2.addNeighbor(node4, 2);
    // node3.addNeighbor(node4, 5);

    DijkstraShortestPath<Integer> pathFinder = new DijkstraShortestPath<>();
    List<Node<Integer>> shortestPath = pathFinder.findShortestPath(node1, node4);

    System.out.println("Shortest Path: " + shortestPath);
  }
}
