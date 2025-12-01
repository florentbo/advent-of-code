package be.bonamis.advent.year2025;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.regex.*;

import be.bonamis.advent.year2025.Day01.Input.Line.Direction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day01 extends TextDaySolver {

  private final Input input;

  public Day01(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day01(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  record Input(List<Line> lines) {
    record Line(Direction direction, int distance) {

      static Line of(String lineStr) {
        Direction direction = Direction.valueOf(lineStr.substring(0, 1));
        int distance = Integer.parseInt(lineStr.substring(1));
        return new Line(direction, distance);
      }

      enum Direction {
        L,
        R;
      }
    }

    int zeroCount() {
      int startingZeroes = 0;
      int startingIndex = 50;
      for (Line line : this.lines()) {
        Direction direction = line.direction;
        switch (direction) {
          case L -> startingIndex -= line.distance;
          case R -> startingIndex += line.distance;
        }
        startingIndex = ((startingIndex % 100) + 100) % 100;
        System.out.println(startingIndex);

        if (startingIndex == 0) {
          startingZeroes++;
        }
      }
      return startingZeroes;
    }

    public long countZeroCrossings() {
      int startingZeroes = 0;
      int startingIndex = 50;

      for (Line line : this.lines()) {
        int start = startingIndex;
        Direction direction = line.direction;
        switch (direction) {
          case L -> startingIndex -= line.distance;
          case R -> startingIndex += line.distance;
        }
        startingIndex = ((startingIndex % 100) + 100) % 100;
        int end = startingIndex;

        int count = countZeroCrossings(start, end, line.distance, direction);
        startingZeroes += count;
      }

      return startingZeroes;
    }

    int countZeroCrossings(int start, int end, int distance, Direction direction) {
      int count = distance / 100;
      if (start == 0) {
        return count;
      }
      boolean wrapped = (direction == Direction.L) ? start < end : start > end;
      if (wrapped || end == 0) {
        count += 1;
      }
      return count;
    }

    static Input of(List<String> lines) {
      List<Line> lineList = lines.stream().map(Line::of).toList();
      return new Input(lineList);
    }
  }

  @Override
  public long solvePart01() {
    return this.input.zeroCount();
  }

  @Override
  public long solvePart02() {
    return this.input.countZeroCrossings();
  }
}
