package be.bonamis.advent.year2024;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Rover;
import org.junit.jupiter.api.*;

class Day12Test {
  @Test
  void solvePart01() {

    final String INPUT =
        """
                +-+-+-+-+
                |A A A A|
                +-+-+-+-+     +-+
                          |D|
                +-+-+   +-+   +-+
                |B B|   |C|
                +   +   + +-+
                |B B|   |C C|
                +-+-+   +-+ +
                          |C|
                +-+-+-+   +-+
                |E E E|
                +-+-+-+
                Plants of the same ty
                """;

    String SECOND_INPUT =
        """
AAAA
BBCD
BBCC
EEEC
""";


    InputStream inputStream = new ByteArrayInputStream(SECOND_INPUT.getBytes());
    Day12 day12 = new Day12(inputStream);
    CharGrid grid = day12.grid();
    var cPoints = grid.stream().filter(point -> grid.get(point).equals('C')).toList();
    System.out.println(cPoints);
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    List<Fence> list =
        cPoints.stream()
            .flatMap(
                p -> {
                  System.out.println("point: " + p);
                  List<Fence> neighbours = neighbours(p);
                  System.out.println(neighbours);
                  return neighbours.stream()
                      .filter(
                          not(
                              f -> {
                                Point point = new Point(f.x, f.y);
                                return cPoints.contains(point);
                              }));
                })
            .toList();

    /*
    AAAA
    BBCD
    BBCC
    EEEC
     */

    List<Fence> fences =
        List.of(
            Fence.of(1, 1, Rover.Direction.NORTH),
            Fence.of(3, 1, Rover.Direction.NORTH),
            Fence.of(2, 0, Rover.Direction.EAST),
            Fence.of(1, 2, Rover.Direction.NORTH),
            Fence.of(2, 3, Rover.Direction.EAST),
            Fence.of(3, 1, Rover.Direction.EAST),
            Fence.of(4, 2, Rover.Direction.NORTH),
            Fence.of(2, 3, Rover.Direction.NORTH),
            Fence.of(4, 3, Rover.Direction.NORTH),
            Fence.of(3, 4, Rover.Direction.EAST));

    assertThat(list).containsExactlyInAnyOrderElementsOf(fences);
    var set = new HashSet<>(list);
    assertThat(set).containsExactlyInAnyOrderElementsOf(list);

    String THIRD_INPUT =
        """
      OOOOO
      OXOXO
      OOOOO
      OXOXO
      OOOOO
      """;

      InputStream inputStream2 = new ByteArrayInputStream(THIRD_INPUT.getBytes());
  }

  public List<Fence> neighbours(Point point) {
    List<Fence> neighbours = new ArrayList<>();
    neighbours.add(Fence.of(point.x - 1, point.y, Rover.Direction.NORTH));
    neighbours.add(Fence.of(point.x + 1, point.y, Rover.Direction.NORTH));
    neighbours.add(Fence.of(point.x, point.y - 1, Rover.Direction.EAST));
    neighbours.add(Fence.of(point.x, point.y + 1, Rover.Direction.EAST));
    return neighbours;
  }

  record Fence(int x, int y, Rover.Direction direction) {
    static Fence of(int x, int y, Rover.Direction direction) {
      return new Fence(x, y, direction);
    }
  }
}
