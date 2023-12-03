package be.bonamis.advent.common;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class CharGrid {

  private final char[][] data;
  private final int width;
  private final int height;

  public CharGrid(char[][] input) {
    this.data = input;
    this.height = input.length;
    this.width = input[0].length;
  }

  public Stream<Point> stream() {
    return IntStream.range(0, data.length)
        .boxed()
        .flatMap(x -> IntStream.range(0, data[x].length).mapToObj(y -> new Point(x, y)));
  }

  public Character get(Point p) {
    if (p.x >= 0 && p.x < data.length && p.y >= 0 && p.y < data[0].length) {
      return data[p.x][p.y];
    }
    return null;
  }

  public Long number(Point p) {
    return (long) Character.getNumericValue(get(p));
  }

  public void printArray() {
    System.out.println("start");
    for (char[] ints : this.data) {
      for (char anInt : ints) {
        System.out.print(anInt);
      }
      System.out.println();
    }
    System.out.println("end");
  }

  public void set(Point point, char i) {
    data[point.x][point.y] = i;
  }

  public void consume(Consumer<Point> consumer) {
    for (int x = 0; x < data.length; x++) {
      for (int y = 0; y < data[x].length; y++) {
        consumer.accept(new Point(x, y));
      }
    }
  }

  public List<List<Point>> lines() {
    List<List<Point>> lines = new ArrayList<>();
    for (int x = 0; x < data.length; x++) {
      List<Point> line = new ArrayList<>();
      for (int y = 0; y < data[x].length; y++) {
        line.add(new Point(x, y));
      }
      lines.add(line);
    }
    return lines;
  }

  public List<Point> neighbours(Point point) {
    List<Point> neighbours = new ArrayList<>();
    neighbours.add(new Point(point.x - 1, point.y - 1));
    neighbours.add(new Point(point.x - 1, point.y));
    neighbours.add(new Point(point.x - 1, point.y + 1));
    neighbours.add(new Point(point.x, point.y - 1));
    neighbours.add(new Point(point.x, point.y + 1));
    neighbours.add(new Point(point.x + 1, point.y));
    neighbours.add(new Point(point.x + 1, point.y - 1));
    neighbours.add(new Point(point.x + 1, point.y + 1));
    return neighbours.stream()
        .filter(
            p ->
                p.getX() >= 0
                    && p.getY() >= 0
                    && p.getX() < this.getHeight()
                    && p.getY() < this.getWidth())
        .toList();
  }
}
