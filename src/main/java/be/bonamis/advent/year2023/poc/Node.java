package be.bonamis.advent.year2023.poc;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Node<T> {

  private T name;

  private LinkedList<Node<T>> shortestPath = new LinkedList<>();

  private Integer distance = Integer.MAX_VALUE;

  private Map<Node<T>, Integer> adjacentNodes = new HashMap<>();

  public Node(T name) {
    this.name = name;
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
  public String toString() {
    return "Node{" +
            "name='" + name + '\'' +
            '}';
  }
}
