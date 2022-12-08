package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Grid;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day08 extends DaySolver<String> {

    public Day08(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        TreeHouse treeHouse = new TreeHouse(this.puzzle);
        return treeHouse.findVisibleTrees();
    }

    @Override
    public long solvePart02() {
        return this.puzzle.size() + 1;
    }

    record TreeHouse(Grid trees) {

        public TreeHouse(List<String> lines) {
            this(new Grid(lines.parallelStream().map(l -> l.trim().split(""))
                    .map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
                    .toArray(int[][]::new)));
        }

        public TreeHouse(int[][] input) {
            this(new Grid(input));
        }

        long findVisibleTrees() {


            trees.printArray();
            trees.stream().forEach(new Consumer<Point>() {
                @Override
                public void accept(Point point) {

                    Integer integer = trees.get(point);

                    boolean pointOnTheEdge = isPointOnTheEdge(point);


                    if (!pointOnTheEdge && integer == 5) {
                        System.out.println("+++++");
                        System.out.println(point + " - value: " + integer);

                        System.out.println("Visible - " + isPointVisible(point));
                        System.out.println("+++++");
                    }

                    //System.out.println(" isPointOnTheEdge - " + pointOnTheEdge);

                }
            });

            return trees.stream().filter(point -> isPointOnTheEdge(point) || isPointVisible(point)).count();
        }

        Set<Integer> adjacentPoints(Point point) {
            IntStream topPoints = getTopPoints(point);
            IntStream bottomPoints = getBottomPoints(point);
            IntStream leftPoints = getLeftPoints(point);
            IntStream rightPoints = getRightPoints(point);

            return Stream.of(topPoints, bottomPoints, leftPoints, rightPoints)
                    .flatMap((Function<IntStream, Stream<Integer>>) IntStream::boxed)
                    .collect(Collectors.toSet());
        }

        private IntStream getRightPoints(Point point) {
            return getLinePoints(point.y + 1, this.trees.getWidth(), walkHorizontally(this.trees, point));
        }

        private IntStream getLeftPoints(Point point) {
            return getLinePoints(0, point.y, walkHorizontally(trees, point));
        }

        private IntStream getBottomPoints(Point point) {
            return getLinePoints(point.x + 1, this.trees.getHeight(), walkVertically(this.trees, point));
        }

        IntStream getTopPoints(Point point) {
            return getLinePoints(0, point.x, walkVertically(this.trees, point));
        }

        private IntStream getLinePoints(int start, int end, IntUnaryOperator trees) {
            return IntStream.range(start, end).map(trees);
        }

        boolean isPointOnTheEdge(Point point) {
            double x = point.getX();
            double y = point.getY();
            return x == 0 || y == 0 || x == trees().getHeight() - 1 || y == trees().getWidth() - 1;
        }

        private static IntUnaryOperator walkVertically(Grid trees, Point point) {
            return operand -> trees.get(new Point(operand, point.y));
        }

        private static IntUnaryOperator walkHorizontally(Grid trees, Point point) {
            return operand -> trees.get(new Point(point.x, operand));
        }

        public boolean isPointVisible(Point point) {
            Integer value = this.trees.get(point);
            System.out.println(getTopPoints(point).boxed().toList());
            System.out.println(getRightPoints(point).boxed().toList());
            System.out.println(getBottomPoints(point).boxed().toList());
            System.out.println(getLeftPoints(point).boxed().toList());
            boolean isOneTopTreeBigger = isTreeBigger(getTopPoints(point), value);
            boolean isOneRightTreeBigger = isTreeBigger(getRightPoints(point), value);
            boolean isOneBottomTreeBigger = isTreeBigger(getBottomPoints(point), value);
            boolean isOneLeftTreeBigger = isTreeBigger(getLeftPoints(point), value);
            return !isOneTopTreeBigger || !isOneRightTreeBigger || !isOneBottomTreeBigger || !isOneLeftTreeBigger;
        }

        private boolean isTreeBigger(IntStream point, Integer value) {
            return point.anyMatch(i -> i >= value);
        }
    }
}
