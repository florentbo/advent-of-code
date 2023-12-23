package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Command;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.year2023.poc.DijkstraAlgorithm;
import be.bonamis.advent.year2023.poc.Graph;
import be.bonamis.advent.year2023.poc.Node;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day23 extends DaySolver<String> {

  private static final char PATH = '.';
  private static final char FOREST = '#';
  private static final char NORTH_STEEP = '^';
  private static final char SOUTH_STEEP = 'v';
  private static final char EAST_STEEP = '>';
  private static final char WEST_STEEP = '<';

  private final CharGrid grid;

  public Day23(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    Position startPosition = Position.of(1, 0);
    Position endPosition = Position.of(grid.getWidth() - 2, grid.getHeight() - 1);
    Rover start = new Rover(EAST, startPosition);
    char dataStart = data(start);
    log.debug("dataStart {}", dataStart);
    Rover end = new Rover(EAST, endPosition);
    char dataEnd = data(end);
    log.debug("dataEnd {}", dataEnd);

    Graph graph = new Graph();
    Map<Point, Node<Point>> nodes = new HashMap<>();
    grid.consume(
        point -> {
          if (isNotForest(point)) {
            Node<Point> node = new Node<>(point);
            nodes.put(point, node);
            graph.addNode(node);
            for (var adjacent : grid.neighbours(point)) {
              if (isNotForest(point)) {
                Node<Point> adjacentNode = nodes.get(adjacent);
                node.addDestination(adjacentNode, 1);
              }
            }
          }
        });

    final var source = startPosition.toPoint();
    final var sink = endPosition.toPoint();
    Node<Point> sourceNode = nodes.get(source);
    Node<Point> sinkNode = nodes.get(sink);
    DijkstraAlgorithm.Result<Point> result =
        new Day17.PointDijkstraAlgorithm()
            .calculateShortestPathFromSource(sourceNode, sinkNode, false);

    result.path().forEach(n -> log.debug("{}", n.getName()));
    return result.distance();
  }

  private boolean isNotForest(Point point) {
    Character data = grid.get(point);
    boolean condition = data != FOREST;
    return condition;
  }

  private char data(Rover rover) {
    return grid.get(rover.position());
  }

  @Override
  public long solvePart02() {
    return 9999L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/23/2023_23_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day23 day = new Day23(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
