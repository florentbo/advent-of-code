package be.bonamis.advent.year2022;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

class Day08Test {

    private static final String CODE_TXT = "2022/08/2022_08_00_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(new Day08(lines).solvePart01()).isEqualTo(21);
    }

    @Test
    void getAdjacentPoints_given_the_point_in_the_middle_has_4_points() {
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };

        Day08.TreeHouse treeHouse = new Day08.TreeHouse(input);
        Point point = new Point(1, 1);
        assertThat(treeHouse.trees().get(point)).isEqualTo(5);
        assertThat(treeHouse.adjacentPoints(point)).hasSize(5);
        assertThat(treeHouse.getTopPoints(point)).containsExactlyInAnyOrder(2);
        assertThat(treeHouse.adjacentPoints(point)).containsExactlyInAnyOrder(2, 4, 6, 8, 11);

        Point topMiddle5 = new Point(1, 2);
        assertThat(treeHouse.trees().get(topMiddle5)).isEqualTo(6);
        assertThat(treeHouse.getTopPoints(topMiddle5)).containsExactlyInAnyOrder(3);
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

        Day08.TreeHouse treeHouse = new Day08.TreeHouse(input);
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
        Day08.TreeHouse treeHouse = new Day08.TreeHouse(lines);
        Point point = new Point(xCoordinate, yCoordinate);
        assertThat(treeHouse.trees().get(point)).isEqualTo(5);
        assertThat(treeHouse.isPointVisible(point)).isEqualTo(expected);

    }

    @Test
    @Disabled
    void solvePart02() {
        assertThat(new Day08(getLines(CODE_TXT)).solvePart02()).isEqualTo(24);
    }


    private static void printPoints(IntStream northPoints) {
        Set<Integer> points = northPoints.boxed().collect(Collectors.toSet());
        System.out.println(points);
    }


}
