package be.bonamis.advent.year2024;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.*;
import be.bonamis.advent.utils.marsrover.Rover;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

class Day16Test {

  @Test
  void test1() {



    String THIRD_INPUT =
        """
              ###############
              #.......#....E#
              #.#.###.#.###.#
              #.....#.#...#.#
              #.###.#####.#.#
              #.#.#.......#.#
              #.#.#####.###.#
              #...........#.#
              ###.#.#####.#.#
              #...#.....#.#.#
              #.#.#.###.#.#.#
              #.....#...#.#.#
              #.###.#.#.#.#.#
              #S..#.....#...#
              ###############
              """;

    Day16 day16 = new Day16(new ByteArrayInputStream(THIRD_INPUT.getBytes()));
    day16.solvePart01();
  }
}
