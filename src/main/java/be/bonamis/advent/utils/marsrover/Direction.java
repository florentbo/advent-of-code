package be.bonamis.advent.utils.marsrover;

import lombok.Getter;

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
