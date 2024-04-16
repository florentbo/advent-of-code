package be.bonamis.advent.common;

import java.awt.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class Grid {

    private final int[][] data;
    private final int width;
    private final int height;

    public Grid(int[][] input) {
        this.data = input;
        this.height = input.length;
        this.width = input[0].length;
    }

  public Stream<Point> stream() {
    return IntStream.range(0, data.length)
        .boxed()
        .flatMap(x -> IntStream.range(0, data[x].length).mapToObj(y -> new Point(x, y)));
  }

    public Integer get(Point p) {
        if (p.x >= 0 && p.x < data.length && p.y >= 0 && p.y < data[0].length) {
            return data[p.x][p.y];
        }
        return null;
    }

    public void printArray() {
        for (int[] ints : this.data) {
            for (int number : ints) {
                System.out.print(number);//#####.....
            }
            System.out.println();
        }
    }

    public void set(Point point, int i) {
        data[point.x][point.y] = i;
    }

    public void consume(Consumer<Point> consumer) {
        for (int x = 0; x < data.length; x++) {
            for (int y = 0; y < data[x].length; y++) {
                consumer.accept(new Point(x, y));
            }
        }
    }


}
