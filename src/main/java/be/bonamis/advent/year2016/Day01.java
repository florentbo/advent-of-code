package be.bonamis.advent.year2016;

import static be.bonamis.advent.DayDataRetriever.*;
import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.*;

import be.bonamis.advent.utils.marsrover.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day01 extends TextDaySolver {

  public Day01(InputStream sample) {
    super(sample);
  }

  static Rover.Command toCommand(char c) {
    return switch (c) {
      case 'R' -> RIGHT;
      case 'L' -> LEFT;
      default -> throw new IllegalArgumentException("Invalid position " + c);
    };
  }

  static long distance(Position position) {
    long x = Math.abs(position.x());
    long y = Math.abs(position.y());
    return x + y;
  }

  @Override
  public long solvePart01() {
    Moves moves = Moves.from(this.puzzle.get(0));
    return distance(moves.lastRoverPosition());
  }

  @Override
  public long solvePart02() {
    Moves moves = Moves.from(this.puzzle.get(0));
    return distance(moves.firstLocationVisitedTwice());

  }

  public static void main(String[] args) {
    String puzzleInputUrl = dayUrl(2016, 1) + "/input";
    InputStream inputStream = downloadInput(puzzleInputUrl);
    Day01 day05 = new Day01(inputStream);
    System.out.println("Part 1: " + day05.solvePart01());
    System.out.println("Part 2: " + day05.solvePart02());
  }

  record Moves(List<Move> moves) {

    static Moves from(String commands) {
      return new Moves(
          Arrays.stream(commands.split(","))
              .map(String::trim)
              .map(Move::from)
              .collect(Collectors.toList()));
    }

    Position lastRoverPosition() {
      Rover santa = new Rover(NORTH, Position.of(0, 0));
      for (Move move : moves) {
        Rover.Command position = move.position();
        santa = santa.move(position);
        for (int i = 0; i < move.distance(); i++) {
          santa = santa.move(FORWARD);
        }
      }
      return santa.position();
    }

    public Position firstLocationVisitedTwice() {
      Rover santa = new Rover(NORTH, Position.of(0, 0));
      List<Position> positions = new ArrayList<>();
      for (Move move : moves) {
        Rover.Command position = move.position();
        santa = santa.move(position);
        for (int i = 0; i < move.distance(); i++) {
          santa = santa.move(FORWARD);
          Position latest = santa.position();
          if (positions.contains(latest)) {
            return latest;
          }
          positions.add(latest);
        }
      }
      throw new IllegalStateException("No location visited twice");
    }
  }

  record Move(Rover.Command position, int distance) {

    static Move of(Rover.Command position, int distance) {
      return new Move(position, distance);
    }

    static Move from(String command) {
      return Move.of(toCommand(command.charAt(0)), Integer.parseInt(command.substring(1)));
    }
  }
}
