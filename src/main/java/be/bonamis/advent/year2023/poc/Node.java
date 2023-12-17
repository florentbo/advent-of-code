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

  @Override
  public String toString() {
    return "Node{" +
            "name='" + name + '\'' +
            '}';
  }
}
