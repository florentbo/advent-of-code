package be.bonamis.advent.year2018;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day04 extends TextDaySolver {

  private final Input input;

  public Day04(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day04(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  record Input(String test) {
    record SecurityGuard(int id, List<Shift> shifts) {}

    record Shift(LocalDateTime date, List<Event> events) {}

    record Event(LocalDateTime time, EventType type) {}

    enum EventType {
      BEGINS_SHIFT,
      FALLS_ASLEEP,
      WAKES_UP;
    }

    // [1518-11-01 00:00] Guard #10 begins shift
    record InputLine(LocalDateTime dateTime, String raw) {
      static InputLine of(String line) {
        Pattern pattern = Pattern.compile("\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2})\\] (.+)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
          LocalDateTime dateTime =
              LocalDateTime.parse(
                  matcher.group(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
          String event = matcher.group(2);
          return new InputLine(dateTime, event);
        }

        throw new RuntimeException();
      }

      static InputLine of(LocalDateTime dateTime, String raw) {
        return new InputLine(dateTime, raw);
      }
    }

    static Input of(List<String> puzzle) {

      return new Input("");
    }
  }

  @Override
  public long solvePart01() {
    return 99L;
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
