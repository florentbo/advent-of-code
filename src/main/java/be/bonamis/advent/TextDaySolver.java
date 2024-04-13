package be.bonamis.advent;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static be.bonamis.advent.year2015.Day05.lines;

public abstract class TextDaySolver extends DaySolver<String> {

  protected TextDaySolver(List<String> puzzle) {
    super(puzzle);
  }

  protected TextDaySolver(String input) {
    super((Arrays.asList(input.split("\n"))));
  }

  protected TextDaySolver(InputStream input) {
    super(lines(input));
  }
}
