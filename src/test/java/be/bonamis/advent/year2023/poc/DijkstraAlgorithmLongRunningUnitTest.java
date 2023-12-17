package be.bonamis.advent.year2023.poc;

import org.junit.jupiter.api.Test;


class DijkstraAlgorithmLongRunningUnitTest {

  @Test
  public void whenSPPSolved_thenCorrect() {

    Node<String> nodeA = new Node<>("A");
    Node<String> nodeB = new Node<>("B");
    Node<String> nodeC = new Node<>("C");
    Node<String> nodeD = new Node<>("D");
    Node<String> nodeE = new Node<>("E");
    Node<String> nodeF = new Node<>("F");

    nodeA.addDestination(nodeB, 10);
    nodeA.addDestination(nodeC, 15);

    nodeB.addDestination(nodeD, 12);
    nodeB.addDestination(nodeF, 15);

    nodeC.addDestination(nodeE, 10);

    nodeD.addDestination(nodeE, 2);
    nodeD.addDestination(nodeF, 1);

    nodeF.addDestination(nodeE, 5);

    Graph graph = new Graph();

    graph.addNode(nodeA);
    graph.addNode(nodeB);
    graph.addNode(nodeC);
    graph.addNode(nodeD);
    graph.addNode(nodeE);
    graph.addNode(nodeF);

    DijkstraAlgorithm.Result<String> result = new DijkstraAlgorithm<String>().calculateShortestPathFromSource(nodeA, nodeF, false);
    System.out.println(result);

    /*for (Node node : graph.getNodes()) {
      switch (node.getName()) {
        case "B":
          assertEquals(node.getShortestPath(), shortestPathForNodeB);
          break;
        case "C":
          assertEquals(node.getShortestPath(), shortestPathForNodeC);
          break;
        case "D":
          assertEquals(node.getShortestPath(), shortestPathForNodeD);
          break;
        case "E":
          assertEquals(node.getShortestPath(), shortestPathForNodeE);
          break;
        case "F":
          assertEquals(node.getShortestPath(), shortestPathForNodeF);
          break;
      }
    }*/
  }
}
