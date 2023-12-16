package be.bonamis.advent.year2023.poc;

import lombok.*;

@Getter
@Setter
public class TreeNode<T> {
  private T data;
  private TreeNode<T> left;
  private TreeNode<T> right;

  public TreeNode(T data) {
    this.data = data;
    this.left = null;
    this.right = null;
  }
}
