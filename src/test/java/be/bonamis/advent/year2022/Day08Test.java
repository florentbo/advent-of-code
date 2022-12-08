package be.bonamis.advent.year2022;

import be.bonamis.advent.common.Grid;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {

    private static final String CODE_TXT = "2022/08/2022_08_00_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(findVisibleTrees(lines)).isEqualTo(21);
    }

    @Test
    void getAdjacentPoints_given_the_point_in_the_middle_has_4_points() {
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };

        TreeHouse treeHouse = new TreeHouse(input);
        Point point = new Point(1, 1);
        assertThat(treeHouse.trees().get(point)).isEqualTo(5);
        assertThat(treeHouse.adjacentPoints(point)).hasSize(5);
        assertThat(treeHouse.adjacentPoints(point)).containsExactlyInAnyOrder(2, 4, 6, 8, 11);
    }

    @ParameterizedTest
    @CsvSource({
            "0,0, true",
            "1,0, true",
            "2,0, true",
            "3,0, true",
            "3,1, true",
            "3,2, true",
            "2,2, true",
            "1,2, true",
            "0,2, true",
            "0,1, true",
            "1,1, false"})
    void isPointOnTheEdge_ReturnsTrueIfIsOnTheEdgeOfTheForest(int xCoordinate, int yCoordinate, boolean expected) {
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };

        TreeHouse treeHouse = new TreeHouse(input);
        //System.out.println(treeHouse.trees.getHeight());
        Point point = new Point(xCoordinate, yCoordinate);
        //System.out.println(treeHouse.trees.get(point));
        assertThat(treeHouse.isPointOnTheEdge(point)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1, true",
            "1,2, true",
            "2,1, true",
            "1,1, true"})
    void isTreeVisible_ReturnsTrueIfVisibleFromOneSide(int xCoordinate, int yCoordinate, boolean expected) {
        List<String> lines = getLines(CODE_TXT);
        TreeHouse treeHouse = new TreeHouse(lines);
        Point point = new Point(xCoordinate, yCoordinate);
        assertThat(treeHouse.trees().get(point)).isEqualTo(5);
        assertThat(treeHouse.isPointVisible(point)).isEqualTo(expected);

    }

    @Test
    @Disabled
    void solvePart02() {
        assertThat(new Day07(getLines(CODE_TXT)).solvePart02()).isEqualTo(24);
    }

    long findVisibleTrees(List<String> lines) {
        TreeHouse treeHouse = new TreeHouse(lines);
        Grid trees = treeHouse.trees;

        trees.printArray();
        trees.stream().forEach(new Consumer<Point>() {
            @Override
            public void accept(Point point) {

                System.out.println(point + " - " + trees.get(point));
                boolean pointOnTheEdge = treeHouse.isPointOnTheEdge(point);
                if (!pointOnTheEdge) {
                    System.out.println("Visible - " + treeHouse.isPointVisible(point));
                }
                //System.out.println(" isPointOnTheEdge - " + pointOnTheEdge);

            }
        });

        return trees.stream().filter(point -> treeHouse.isPointOnTheEdge(point) || treeHouse.isPointVisible(point)).count();

        //return lines.size();
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
            return getLinePoints(point.x + 1, this.trees.getWidth(), walkHorizontally(this.trees, point));
        }

        private IntStream getLeftPoints(Point point) {
            return getLinePoints(0, point.x, walkHorizontally(trees, point));
        }

        private IntStream getBottomPoints(Point point) {
            return getLinePoints(point.y + 1, this.trees.getHeight(), walkVertically(this.trees, point));
        }

        private IntStream getTopPoints(Point point) {
            return getLinePoints(0, point.y, walkVertically(this.trees, point));
        }

        private IntStream getLinePoints(int startInclusive, int point, IntUnaryOperator trees) {
            return IntStream.range(startInclusive, point).map(trees);
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
   /*         System.out.println(getTopPoints(point).boxed().toList());
            System.out.println(getRightPoints(point).boxed().toList());
            System.out.println(getLeftPoints(point).boxed().toList());
            System.out.println(getBottomPoints(point).boxed().toList());*/
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


    private static void printPoints(IntStream northPoints) {
        Set<Integer> points = northPoints.boxed().collect(Collectors.toSet());
        System.out.println(points);
    }


}
