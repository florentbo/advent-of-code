package be.bonamis.advent.year2023.poc;

import java.util.*;

public class TreeBuilder {

  public static void main(String[] args) {
    String input = "1-2\n1-3\n2-4\n2-5";

    TreeNode<Integer> root = buildTree(input);

    // Perform in-order tree traversal
    System.out.println("In-order traversal:");
    System.out.println(inOrderTraversal(root));
  }

  private static TreeNode<Integer> buildTree(String input) {
    Map<Integer, TreeNode<Integer>> nodes = new HashMap<>();

    String[] lines = input.split("\n");
    for (String line : lines) {
      inputLineHandling(line, nodes);
    }

    return nodes.get(1); // Assuming the root is always labeled as '1'
  }

  private static void inputLineHandling(String line, Map<Integer, TreeNode<Integer>> nodes) {
    String[] tokens = line.split("-");
    int parentData = Integer.parseInt(tokens[0]);
    int childData = Integer.parseInt(tokens[1]);

    dataHandling(nodes, parentData, childData);
  }

  private static void dataHandling(Map<Integer, TreeNode<Integer>> nodes, int parentData, int childData) {
    TreeNode<Integer> parent = nodes.computeIfAbsent(parentData, TreeNode::new);
    TreeNode<Integer> child = nodes.computeIfAbsent(childData, TreeNode::new);

    if (parent.getLeft() == null) {
      parent.setLeft(child);
    } else {
      parent.setRight(child);
    }
  }

  private static List<Integer> inOrderTraversal(TreeNode<Integer> root) {
    List<Integer> result = new ArrayList<>();
    inOrderTraversal(root, result);
    return result;
  }

  private static void inOrderTraversal(TreeNode<Integer> root, List<Integer> result) {
    if (root != null) {
      inOrderTraversal(root.getLeft(), result);
      result.add(root.getData());
      inOrderTraversal(root.getRight(), result);
    }
  }
}
