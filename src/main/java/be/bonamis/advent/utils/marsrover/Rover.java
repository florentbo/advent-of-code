package be.bonamis.advent.utils.marsrover;

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
}
