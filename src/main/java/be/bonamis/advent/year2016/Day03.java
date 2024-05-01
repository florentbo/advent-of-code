package be.bonamis.advent.year2016;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day03 extends TextDaySolver {

  public Day03(InputStream sample) {
    super(sample);
  }

  @Override
  public long solvePart01() {
    return triangles().filter(Triangle::isValid).count();
  }

  private Stream<Triangle> triangles() {
    return this.puzzle.stream().map(Triangle::of);
  }

  @Override
  public long solvePart02() {
    List<Triangle> triangles = triangles().toList();
    long count = 0;
    for (int i = 0; i < triangles.size(); i += 3) {
      Triangle t1 = triangles.get(i);
      Triangle t2 = triangles.get(i + 1);
      Triangle t3 = triangles.get(i + 2);
      count += Triangle.of(t1.a, t2.a, t3.a).isValid() ? 1 : 0;
      count += Triangle.of(t1.b, t2.b, t3.b).isValid() ? 1 : 0;
      count += Triangle.of(t1.c, t2.c, t3.c).isValid() ? 1 : 0;
    }
    return count;
  }

  record Triangle(int a, int b, int c) {
    public boolean isValid() {
      return a + b > c && a + c > b && b + c > a;
    }

    static Triangle of(int a, int b, int c) {
      return new Triangle(a, b, c);
    }

    static Triangle of(String line) {
      String[] split = line.trim().split("\\s+");
      return new Triangle(
          Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
  }
}
