package be.bonamis.advent.year2025;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day02 extends TextDaySolver {

  private final Input input;

  public Day02(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day02(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  record Input(List<Range> ranges) {
    record Range(long start, long end) {
      static Range of(String lineStr) {
        String[] parts = lineStr.split("-");
        long start = Long.parseLong(parts[0]);
        long end = Long.parseLong(parts[1]);
        return new Range(start, end);
      }

      public List<Long> invalids() {
        List<Long> all = LongStream.rangeClosed(start, end).boxed().toList();
        return all.stream().filter(this::isInValid).toList();
      }

      public List<Long> seqInvalids() {
        List<Long> all = LongStream.rangeClosed(start, end).boxed().toList();
        return all.stream().filter(this::sequencesOfSameDigit).toList();
      }

      private boolean isInValid(Long id) {
        String s = id.toString();
        int len = s.length();
        if (len % 2 == 0) {
          String left = s.substring(0, len / 2);
          String right = s.substring(len / 2, len);
          return left.equals(right);
        }
        return false;
      }

      private String getString(int seqLen, int len, String s) {
        String seq = s.substring(0, seqLen);
        int repeatCount = len / seqLen;
        return seq.repeat(repeatCount);
      }

      private boolean sequencesOfSameDigit(Long id) {
        // example: 824824824 -> 824 repeated 3 times

        String s = id.toString();
        int len = s.length();

        return IntStream.rangeClosed(1, len / 2)
            .filter(seqLen -> len % seqLen == 0)
            .mapToObj(i -> getString(i, len, s))
            .anyMatch(rep -> rep.equals(s));
      }
    }

    static Input of(List<String> lines) {
      String[] split = lines.get(0).split(",");
      List<Range> list = Arrays.stream(split).map(Range::of).toList();
      return new Input(list);
    }
  }

  @Override
  public long solvePart01() {
    return this.getInput().ranges().stream()
        .flatMap(range -> range.invalids().stream())
        .mapToLong(Long::longValue)
        .sum();
  }

  @Override
  public long solvePart02() {
    return this.getInput().ranges().stream()
        .flatMap(range -> range.seqInvalids().stream())
        .mapToLong(Long::longValue)
        .sum();
  }
}
