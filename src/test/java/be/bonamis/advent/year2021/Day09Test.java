package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getColumn;
import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.year2021.Day09.Heightmap;
import static be.bonamis.advent.year2021.Day09.Number;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day09Test {

    private Day09 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day09_test.txt");
        day = new Day09(data);
    }

    @Test
    void solvePart01() {
        // 2  1 6 6 15
        assertEquals(15, day.solvePart01());
    }

    @Test
    void numbers() {
        //System.out.println(day.getNumbers().get(0));
    }

    @Test
    void getNumbersSiblings() {
        int[][] input = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11}};
        //Arrays.stream(arr1).map(Arrays::toString).forEach(System.out::println);

        final var firstColumn = getColumn(input, 0).toArray();
        assertEquals(0, firstColumn[0]);
        assertEquals(4, firstColumn[1]);
        assertEquals(8, firstColumn[2]);

        Heightmap heightmap = new Heightmap(input);
        assertThat(heightmap.getHeight()).isEqualTo(3);
        assertThat(heightmap.getWidth()).isEqualTo(4);
        List<Number> numbers = heightmap.getNumbers();
        assertThat(numbers).hasSize(12);

        for (int i = 0; i < numbers.size(); i++) {
            assertThat(numbers.get(i).getValue(input)).isEqualTo(i);
        }

        Number number0 = numbers.get(0);
        Number number5 = numbers.get(5);
//        checkNumber_0_AdjacentLocations(number0);
  //      checkNumber_5_AdjacentLocations(number5);

        assertThat(number0.isLow()).isTrue();
        assertThat(number5.isLow()).isFalse();

        assertThat(heightmap.lowNumbersSet()).containsOnly(0);
        assertThat(heightmap.riskLevels()).containsOnly(1);
        assertThat(heightmap.riskLevelsSum()).isEqualTo(1);

    }

   /* private void checkNumber_0_AdjacentLocations(Number number) {
        assertThat(number.getValue()).isEqualTo(0);
        assertThat(number.getUp()).isEqualTo(null);
        assertThat(number.getDown()).isEqualTo(4);
        assertThat(number.getLeft()).isEqualTo(null);
        assertThat(number.getRight()).isEqualTo(1);
    }
    private void checkNumber_5_AdjacentLocations(Number number) {
        assertThat(number.getValue()).isEqualTo(5);
        assertThat(number.getUp()).isEqualTo(1);
        assertThat(number.getDown()).isEqualTo(9);
        assertThat(number.getLeft()).isEqualTo(4);
        assertThat(number.getRight()).isEqualTo(6);
    }*/


    @Test
    void walk_into_a_number() {

        Heightmap heightmap = day.getHeightmap();
        int[][] bingoCard = heightmap.getBingoCard();
        //heightmap.lowNumbers().forEach(System.out::println);
        Optional<Number> first = heightmap.lowNumbers().filter(number -> number.getColumnIndex() == 2 && number.getLineIndex() == 2).findFirst();
        first.ifPresent(number -> {
            System.out.println("number 5");
            System.out.println(number);
            assertThat(number.getValue(bingoCard)).isEqualTo(5);
        });

       /* Number number = first.orElse(null);
        number.walk();
        System.out.println("number 1 first line");
        heightmap.getAdjacentLocations(0, 1)
                .stream()
                .map(adjacentLocation-> bingoCard[adjacentLocation.getLineIndex()][adjacentLocation.getColumnIndex()])
                .forEach(System.out::println);*/
    }

    @Test
    void solvePart02() {
        assertEquals(5, day.solvePart02());
    }
}
