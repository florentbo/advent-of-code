package be.bonamis.advent.year2017;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day05 extends TextDaySolver {

  public Day05(InputStream sample) {
    super(sample);
  }

  @Override
  public long solvePart01() {
    List<Integer> numbers = this.puzzle.stream().map(Integer::parseInt).toList();
    ModifiedList modify = new ModifiedList(numbers, 0, 0);
    for (int i = 0; i < 1000000; i++) {
      modify = modify(modify, i);
    }
    System.out.println(modify);
    return 99;
  }

  ModifiedList modify(ModifiedList modifiedList, int i) {
    log.debug("received modify: {}", modifiedList);
    List<Integer> numbers = new ArrayList<>(modifiedList.numbers());
    int actualCursor = modifiedList.cursor();
    try {
      // handle the exit
      int offset = numbers.get(actualCursor);

      log.debug("actualCursor: {} offset: {}", actualCursor, offset);

      int newCursor = actualCursor + offset;
      log.debug("actualCursor: {} offset: {} newCursor: {}", actualCursor, offset, newCursor);
      log.debug("numbers before: {}", numbers);
      int adpap = offset > 2 ? -1 : 1;

      numbers.set(actualCursor, numbers.get(actualCursor) + adpap);
      log.debug("numbers after: {}", numbers);
      ModifiedList sent = new ModifiedList(numbers, newCursor, i);
      log.debug("sent: {}", sent);
      return sent;
    } catch (Exception e) {
      throw new RuntimeException("problem at step: " + i);
    }
  }

  record ModifiedList(List<Integer> numbers, int cursor, int steps) {}

  @Override
  public long solvePart02() {
    return 100;
  }
}
