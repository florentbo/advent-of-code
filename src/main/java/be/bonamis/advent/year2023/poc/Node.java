package be.bonamis.advent.year2023.poc;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class Node<T> {

  private T value;

  private LinkedList<Node<T>> shortestPath = new LinkedList<>();

  private Integer distance = Integer.MAX_VALUE;

  private Map<Node<T>, Integer> adjacentNodes = new HashMap<>();

  public Node(T value) {
    this.value = value;
  }

  public void addDestination(Node<T> destination, int distance) {
    adjacentNodes.put(destination, distance);
  }

  public List<Node<T>> getShortestPath() {
    return shortestPath;
  }

  private LinkedList<Node<T>> longestPath = new LinkedList<>();  // New field for storing the longest path


  private Integer longestDistance = Integer.MIN_VALUE;  // New field for storing the length of the longest path


  // New method for updating the longest path
  public void updateLongestPath(List<Node<T>> path, int distance) {
    if (distance > longestDistance) {
      longestDistance = distance;
      longestPath.clear();
      longestPath.addAll(path);
      longestPath.add(this);  // Add the current node to the longest path
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node<?> node = (Node<?>) o;
    return Objects.equals(value, node.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "Node{" +
            "name='" + value + '\'' +
            '}';
  }
}
