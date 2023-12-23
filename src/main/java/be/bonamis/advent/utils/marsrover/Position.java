package be.bonamis.advent.utils.marsrover;

import java.awt.*;

public record Position(long x, long y) {
  public static Position of(Point point) {
    return new Position(point.x, point.y);
  }

  public static Point to(Position position) {
    return new Point((int) position.x(), (int) position.y());
  }

  public Point toPoint() {
    return new Point((int) this.x, (int) this.y);
  }

  public static Position of(long x, long y) {
    return new Position(x, y);
  }
}
