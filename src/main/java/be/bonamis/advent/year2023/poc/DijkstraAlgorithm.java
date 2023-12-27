package be.bonamis.advent.year2023.poc;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.List;

@Slf4j
public class DijkstraAlgorithm<T> {

  public Result<T> calculateShortestPathFromSource(
      Node<T> source, Node<T> destination, boolean extraValidation) {
    source.setDistance(0);

    Set<Node<T>> settledNodes = new HashSet<>();
    Set<Node<T>> unsettledNodes = new HashSet<>();
    unsettledNodes.add(source);

    while (!unsettledNodes.isEmpty()) {
      Node<T> currentSourceNode = getLowestDistanceNode(unsettledNodes);
      unsettledNodes.remove(currentSourceNode);

      if (currentSourceNode.equals(destination)) {
        // If the destination node is reached, return the result
        return new Result<>(destination.getDistance(), destination.getShortestPath());
      }

      for (Map.Entry<Node<T>, Integer> adjacencyPair :
          adjacentNodes(currentSourceNode).entrySet()) {
        Node<T> evaluationAdjacentNode = adjacencyPair.getKey();
        Integer edgeWeight = adjacencyPair.getValue();

        if (!settledNodes.contains(evaluationAdjacentNode)) {
          calculateMinimumDistance(
              evaluationAdjacentNode, edgeWeight, currentSourceNode, extraValidation);
          unsettledNodes.add(evaluationAdjacentNode);
        }
      }
      settledNodes.add(currentSourceNode);
    }

    return new Result<>(-1, null);
  }

  protected Map<Node<T>, Integer> adjacentNodes(Node<T> currentNode) {
    return currentNode.getAdjacentNodes();
  }

  private void calculateMinimumDistance(
      Node<T> evaluationNode, Integer edgeWeight, Node<T> sourceNode, boolean extraValidation) {
    Integer sourceDistance = sourceNode.getDistance();
    if (sourceDistance + edgeWeight < evaluationNode.getDistance()
        && validate(extraValidation, sourceNode, evaluationNode)) {

      evaluationNode.setDistance(sourceDistance + edgeWeight);
      LinkedList<Node<T>> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
      shortestPath.add(sourceNode);
      evaluationNode.setShortestPath(shortestPath);
    }
  }

  private boolean validate(boolean extraValidation, Node<T> sourceNode, Node<T> evaluationNode) {
    boolean noValidationNeeded = !extraValidation;
    return noValidationNeeded || validate(sourceNode, evaluationNode);
  }

  public boolean validate(Node<T> sourceNode, Node<T> evaluationNode) {
    return true;
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
