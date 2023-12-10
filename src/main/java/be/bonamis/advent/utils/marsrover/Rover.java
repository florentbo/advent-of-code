package be.bonamis.advent.utils.marsrover;

import lombok.Getter;

import java.util.Objects;

public record Rover(Direction facingDirection, Position position) {
  public Rover move(String command) {

    if (Objects.equals(command, "f")) {
      return new Rover(
          facingDirection,
          new Position(
              this.position().x() + this.facingDirection().getForwardMoveX(),
              this.position().y() + this.facingDirection().getForwardMoveY()));
    } else if (Objects.equals(command, "b")) {
      return new Rover(
          facingDirection,
          new Position(
              this.position().x() - this.facingDirection().getForwardMoveX(),
              this.position().y() - this.facingDirection().getForwardMoveY()));
    } else {
      Direction newFacingDirection = null;
      switch (facingDirection) {
        case NORTH -> newFacingDirection = Direction.WEST;
        case SOUTH -> newFacingDirection = Direction.EAST;
        case WEST -> newFacingDirection = Direction.SOUTH;
        case EAST -> newFacingDirection = Direction.NORTH;
      }
      if (Objects.equals(command, "r")) {
        newFacingDirection = newFacingDirection.inverse();
      }
      return new Rover(newFacingDirection, new Position(this.position().x(), this.position().y()));
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
  }
}
