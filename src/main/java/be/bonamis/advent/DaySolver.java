package be.bonamis.advent;

import lombok.Getter;

import java.util.*;

@Getter
public abstract class DaySolver<T> implements Day {
  protected final List<T> puzzle;

  protected DaySolver(List<T> puzzle) {
    this.puzzle = puzzle;
  }
}
