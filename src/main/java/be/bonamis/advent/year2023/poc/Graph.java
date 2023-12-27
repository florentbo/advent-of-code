package be.bonamis.advent.year2023.poc;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class Graph<T> {

  private Set<Node<T>> nodes = new HashSet<>();

  public void addNode(Node<T> nodeA) {
    nodes.add(nodeA);
  }

  public void printAllPaths(Node<T> source, Node<T> destination) {
    List<Node<T>> path = new ArrayList<>();
    Map<Node<T>, Boolean> isVisited = new HashMap<>();
    printAllPathsUtil(source, destination, path, isVisited);
  }

  private void printAllPathsUtil(
      Node<T> source, Node<T> destination, List<Node<T>> path, Map<Node<T>, Boolean> isVisited) {
    if (source.equals(destination)) {
      int size = path.size();
      if (size == 94) {
        path.forEach(n -> log.debug("{}", n.getValue()));
      }
      return;
    }
    isVisited.put(source, true);
    Set<Node<T>> adjacentNodes = source.getAdjacentNodes().keySet();
    adjacentNodes.forEach(
        node -> {
          if (!isVisited.getOrDefault(node, false)) {
            path.add(node);
            printAllPathsUtil(node, destination, path, isVisited);
            path.remove(node);
          }
        });
    isVisited.put(source, false);
  }
}
