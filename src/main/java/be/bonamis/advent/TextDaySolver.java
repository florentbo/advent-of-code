package be.bonamis.advent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

  private static List<String> lines(InputStream inputStream) {
    try (InputStream is = inputStream) {
      return new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next().lines().toList();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
