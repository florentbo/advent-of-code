package be.bonamis.advent.year2023.poc;

import java.util.*;

public class DijkstraAlgorithm<T> {

  public Result<T> calculateShortestPathFromSource(Node<T> source, Node<T> destination, boolean extraValidation) {
    source.setDistance(0);

    Set<Node<T>> settledNodes = new HashSet<>();
    Set<Node<T>> unsettledNodes = new HashSet<>();
    unsettledNodes.add(source);

    while (!unsettledNodes.isEmpty()) {
      Node<T> currentNode = getLowestDistanceNode(unsettledNodes);
      unsettledNodes.remove(currentNode);

      if (currentNode.equals(destination)) {
        // If the destination node is reached, return the result
        return new Result<>(destination.getDistance(), destination.getShortestPath());
      }

      for (Map.Entry<Node<T>, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
        Node<T> adjacentNode = adjacencyPair.getKey();
        Integer edgeWeight = adjacencyPair.getValue();

        if (!settledNodes.contains(adjacentNode)) {
          calculateMinimumDistance(adjacentNode, edgeWeight, currentNode, extraValidation);
          unsettledNodes.add(adjacentNode);
        }
      }
      settledNodes.add(currentNode);
    }

    return new Result<>(-1, null);
  }

  private void calculateMinimumDistance(
          Node<T> evaluationNode, Integer edgeWeight, Node<T> sourceNode, boolean extraValidation) {
    Integer sourceDistance = sourceNode.getDistance();
    if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
      evaluationNode.setDistance(sourceDistance + edgeWeight);
      LinkedList<Node<T>> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
      shortestPath.add(sourceNode);
      evaluationNode.setShortestPath(shortestPath);
    }
  }

  private Node<T> getLowestDistanceNode(Set<Node<T>> unsettledNodes) {
    Node<T> lowestDistanceNode = null;
    int lowestDistance = Integer.MAX_VALUE;
    for (Node<T> node : unsettledNodes) {
      int nodeDistance = node.getDistance();
      if (nodeDistance < lowestDistance) {
        lowestDistance = nodeDistance;
        lowestDistanceNode = node;
      }
    }
    return lowestDistanceNode;
  }

  public record Result<T>(int distance, List<Node<T>> path) {}
}
