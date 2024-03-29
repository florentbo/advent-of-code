package be.bonamis.advent;

import java.util.Arrays;
import java.util.List;

public abstract class TextDaySolver extends DaySolver<String> {

  protected TextDaySolver(List<String> puzzle) {
    super(puzzle);
  }

  protected TextDaySolver(String input) {
    super((Arrays.asList(input.split("\n"))));
  }
}
