package be.bonamis.advent.utils.marsrover;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public record Rover(Direction direction, Position position) {

  public Rover move(Command command) {
    return move(command, false);
  }

  public Rover move(Command command, boolean verticalInverse) {

    switch (command) {
      case FORWARD -> {
        int forwardMoveY =
            verticalInverse
                ? -this.direction().getForwardMoveY()
                : this.direction().getForwardMoveY();
        return new Rover(
            direction,
            new Position(
                this.position().x() + this.direction().getForwardMoveX(),
                this.position().y() + forwardMoveY));
      }
      case BACKWARD -> {
        return new Rover(
            direction,
            new Position(
                this.position().x() - this.direction().getForwardMoveX(),
                this.position().y() - this.direction().getForwardMoveY()));
      }
      default -> {
        // Direction newFacingDirection = verticalInverse ? newDirection().verticalInverse() :
        // newDirection();
        Direction newFacingDirection = goLeftDirection();
        if (Objects.equals(command, Command.RIGHT)) {
          newFacingDirection = newFacingDirection.inverse();
        }
        return new Rover(
            newFacingDirection, new Position(this.position().x(), this.position().y()));
      }
    }
  }

  private Direction goLeftDirection() {
    return switch (direction) {
      case NORTH -> Direction.WEST;
      case SOUTH -> Direction.EAST;
      case WEST -> Direction.SOUTH;
      case EAST -> Direction.NORTH;
    };
  }

  @Getter
  public enum Command {
    FORWARD,

    BACKWARD,

    LEFT,

    RIGHT;

    public Command inverse() {
      return switch (this) {
        case FORWARD -> BACKWARD;
        case BACKWARD -> FORWARD;
        case LEFT -> RIGHT;
        case RIGHT -> LEFT;
      };
    }
  }

  @Getter
  public enum Direction {
    NORTH(0, 1),
    SOUTH(0, -1),
    WEST(-1, 0),
    EAST(1, 0);

    private final int forwardMoveY;
    private final int forwardMoveX;

    Direction(int x, int y) {
      this.forwardMoveX = x;
      this.forwardMoveY = y;
    }

    public Direction inverse() {
      return switch (this) {
        case NORTH -> Direction.SOUTH;
        case SOUTH -> Direction.NORTH;
        case WEST -> Direction.EAST;
        case EAST -> Direction.WEST;
      };
    }

    public Direction verticalInverse() {
      return switch (this) {
        case NORTH -> Direction.SOUTH;
        case SOUTH -> Direction.NORTH;
        case WEST -> Direction.WEST;
        case EAST -> Direction.EAST;
      };
    }
  }
}
