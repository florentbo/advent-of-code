package be.bonamis.advent;

import be.bonamis.advent.year2017.Day04;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DayDataRetrieverTest {

  @Test
  void extractYear() {
    assertThat(DayDataRetriever.extractYear(Day04.class)).isEqualTo(2017);
  }

  @Test
  void extractDay() {
    assertThat(DayDataRetriever.extractDay(Day04.class)).isEqualTo(4);
  }

  @Test
  void dayUrl() {
    assertThat(DayDataRetriever.dayUrl(2017, 4)).isEqualTo("https://adventofcode.com/2017/day/4");
  }
}
