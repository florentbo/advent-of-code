package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day03 extends TextDaySolver {

  public Day03(InputStream inputStream) {
    super(inputStream);
  }

  public Day03(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    long result = 0;
    for (String input : this.puzzle) {
      log.info("s={}", input);

      Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
      Matcher matcher = pattern.matcher(input);

      while (matcher.find()) {
        long number1 = Long.parseLong(matcher.group(1));
        long number2 = Long.parseLong(matcher.group(2));
        log.info("number1={}", number1);
        log.info("number2={}", number2);
        result += number1 * number2;
      }
    }

    return result;
  }

  @Override
  public long solvePart02() {
    long result = 0;
    boolean enabled = true;
    for (String input : this.puzzle) {
      log.info("s={}", input);
      Pattern pattern = Pattern.compile("(do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\))");
      Matcher matcher = pattern.matcher(input);

      while (matcher.find()) {
        log.info("found: {}", matcher.group(0));
        String match = matcher.group(1);
        if (match.startsWith("mul")) {
          long number1 = Long.parseLong(matcher.group(2));
          long number2 = Long.parseLong(matcher.group(3));
          log.info("mul: number1={}, number2={}", number1, number2);
          if (enabled) {
            result += number1 * number2;
          }
        } else {
          String dont = "don't";
          enabled = !match.startsWith(dont);
          log.info("enabled={}", enabled);
        }
      }
    }
    return result;
  }
}
