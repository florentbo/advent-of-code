package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import be.bonamis.advent.year2023.Day19.Rating;
import be.bonamis.advent.year2023.Day19.WorkFlow;
import be.bonamis.advent.year2023.Day19.WorkFlow.Rule;
import java.util.*;
import java.util.List;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
class Day22Test {

  private Day22 day;

  @BeforeEach
  void setUp() {
    String sample =
        """

        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9

                      """;
    day = new Day22(sample);
  }
}
