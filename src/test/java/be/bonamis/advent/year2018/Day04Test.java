package be.bonamis.advent.year2018;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2018.Day04.Input.InputLine;

class Day04Test {

  @Test
  void inputLineOf() {
    assertThat(InputLine.of("[1518-11-01 00:00] Guard #10 begins shift"))
        .isEqualTo(InputLine.of(LocalDateTime.of(1518, 11, 1, 0, 0), "Guard #10 begins shift"));
  }

  String sampleInput =
	  """
1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-04 00:36] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:46] wakes up
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up
                                  """;

  String sortedSampleInput =
	  """
1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up
""";

  @Test
  void inputLineO2f() {
	  /*assertThat(Day04.Input.of("sample").puzzleLines().get(0))
			  .isEqualTo(InputLine.of(LocalDateTime.of(1518, 11, 1, 0, 0), "Guard #10 begins shift"));*/
  }


}
