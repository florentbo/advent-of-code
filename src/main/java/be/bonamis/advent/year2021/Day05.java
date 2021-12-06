package be.bonamis.advent.year2021;

import static be.bonamis.advent.year2021.Day05.LineSegment.Point;
import static be.bonamis.advent.year2021.Day05.LineSegment.PointWrapper;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import be.bonamis.advent.DaySolver;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
public class Day05 extends DaySolver<String> {

    private final List<LineSegment> lines;

    public Day05(List<String> puzzle) {
        super(puzzle);
        this.lines = this.puzzle.stream().map(LineSegment::new).collect(toList());
    }

    @Override
    public long solvePart01() {
        return getLinesOverLapsCount(false);
    }

    private long getLinesOverLapsCount(boolean withDiagonals) {
        var points = this.lines
                                                        .stream()
                                                        .flatMap(line -> line.getPointWrappers(withDiagonals).stream())
                                                        .collect(toList());

        final Map<Point, List<PointWrapper>> pointListMap = points.stream()
                .collect(groupingBy(PointWrapper::getPoint));

        return pointListMap.values().stream().filter(pointList -> pointList.size() > 1).count();
    }

    @Override
    public long solvePart02() {
        return getLinesOverLapsCount(true);
    }

    @AllArgsConstructor
    @Getter
    static class LineSegment {
        private final Point start;
        private final Point end;

        public LineSegment(String line) {
            String[] split = line.split("->");
            this.start = new Point((split[0].trim()));
            this.end = new Point((split[1].trim()));
        }

        public List<Point> getPoints(boolean withDiagonals) {
            if ((this.start.getX() == this.end.getX())) {
                int start = Math.min(this.start.getY(), this.end.getY());
                int end = Math.max(this.start.getY(), this.end.getY());
                List<Point> points = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    points.add(new Point(this.start.getX(), i));
                }
                return points;
            } else if ((this.start.getY() == this.end.getY())) {
                int start = Math.min(this.start.getX(), this.end.getX());
                int end = Math.max(this.start.getX(), this.end.getX());
                List<Point> points = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    points.add(new Point(i, this.start.getY()));
                }
                return points;
            } else if (withDiagonals) {
                List<Point> points = new ArrayList<>();
                Point startPoint = this.start.getX() < this.end.getX() ? this.start : this.end;
                Point endPoint = this.start.getX() < this.end.getX() ? this.end : this.start;
                int startX = startPoint.getX();
                int endX = endPoint.getX();
                if ((endPoint.getY()>startPoint.getY())) {
                    for (int x =0; x <= endX - startX; x++) {
                        points.add(new Point(startX+x, startPoint.getY() + x));
                    }
                } else {
                    for (int x =0; x <= endX - startX; x++) {
                        points.add(new Point(startX+x, startPoint.getY() - x));
                    }
                }

                return points;
            }

            return Collections.emptyList();
        }

        public List<PointWrapper> getPointWrappers(boolean withDiagonals) {
            return this.getPoints(withDiagonals).stream().map(PointWrapper::new).collect(toList());
        }

        static class PointWrapper {
            private final int amount = 1;
            private final Point point;

            public PointWrapper(Point point) {
                this.point = point;
            }

            public Point getPoint() {
                return point;
            }
        }

        @AllArgsConstructor
        @Getter
        @EqualsAndHashCode
        @ToString
        static class Point {
            private final int x;
            private final int y;

            public Point(String line) {
                String[] split = line.split(",");
                this.x = Integer.parseInt(split[0]);
                this.y = Integer.parseInt(split[1]);
            }

            public int getAngle(Point target) {
                int angle = (int) Math.toDegrees(Math.atan2(target.y - y, target.x - x));
                if(angle < 0){
                    angle += 360;
                }
                return angle;
            }

            public boolean isDiagonal(Point target) {
                return getAngle(target) == 45;
            }
        }
    }
}
