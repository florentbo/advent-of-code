package be.bonamis.advent.year2023.poc;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Graph<T> {

  private Set<Node<T>> nodes = new HashSet<>();

  public void addNode(Node<T> nodeA) {
    nodes.add(nodeA);
  }

}
