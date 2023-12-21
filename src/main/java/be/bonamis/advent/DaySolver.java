package be.bonamis.advent;

import java.util.*;

public abstract class DaySolver<T> implements Day {
  protected final List<T> puzzle;

  protected DaySolver(List<T> puzzle) {
    this.puzzle = puzzle;
  }
}
