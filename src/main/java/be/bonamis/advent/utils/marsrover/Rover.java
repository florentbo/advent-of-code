package be.bonamis.advent.utils.marsrover;

import java.util.Objects;

public record Rover(FacingDirection facingDirection, Position position) {
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
      FacingDirection newFacingDirection = null;
      switch (facingDirection) {
        case NORTH -> newFacingDirection = FacingDirection.WEST;
        case SOUTH -> newFacingDirection = FacingDirection.EAST;
        case WEST -> newFacingDirection = FacingDirection.SOUTH;
        case EAST -> newFacingDirection = FacingDirection.NORTH;
      }
      if (Objects.equals(command, "r")) {
        newFacingDirection = newFacingDirection.inverse();
      }
      return new Rover(newFacingDirection, new Position(this.position().x(), this.position().y()));
    }
  }
}
