package be.bonamis.advent.utils.marsrover;

import lombok.Getter;

import java.util.Objects;

public record Rover(Direction facingDirection, Position position) {
  public Rover move(Command command) {

    switch (command) {
      case FORWARD -> {
        return new Rover(
            facingDirection,
            new Position(
                this.position().x() + this.facingDirection().getForwardMoveX(),
                this.position().y() + this.facingDirection().getForwardMoveY()));
      }
      case BACKWARD -> {
        return new Rover(
            facingDirection,
            new Position(
                this.position().x() - this.facingDirection().getForwardMoveX(),
                this.position().y() - this.facingDirection().getForwardMoveY()));
      }
      default -> {
        Direction newFacingDirection = newDirection();
        if (Objects.equals(command, Command.RIGHT)) {
          newFacingDirection = newFacingDirection.inverse();
        }
        return new Rover(
            newFacingDirection, new Position(this.position().x(), this.position().y()));
      }
    }
  }

  private Direction newDirection() {
    return switch (facingDirection) {
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

    RIGHT
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
  }
}
