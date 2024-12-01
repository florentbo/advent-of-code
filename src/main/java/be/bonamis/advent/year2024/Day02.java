package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day02 extends TextDaySolver {

  public Day02(InputStream inputStream) {
    super(inputStream);
  }

  public Day02(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return 2024;
  }

  @Override
  public long solvePart02() {
    return 2025;
  }
}
