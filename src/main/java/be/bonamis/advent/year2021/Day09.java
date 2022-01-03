package be.bonamis.advent.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.year2021.Day09.Number.AdjacentLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
//@Slf4j
public class Day09 extends DaySolver<String> {

    private final Heightmap heightmap;

    public Day09(List<String> puzzle) {
        super(puzzle);
        this.heightmap = new Heightmap(puzzle.parallelStream().map(l -> l.trim().split(""))
                .map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new));
    }

    @Override
    public long solvePart01() {
        return heightmap.riskLevelsSum();
    }

    @Getter
    static class Heightmap {
        private final int[][] bingoCard;
        private final int width;
        private final int height;
        private final List<Number> numbers;

        public Heightmap(int[][] input) {
            this.bingoCard = input;
            this.height = input.length;
            this.width = input[0].length;
            this.numbers = createNumbers();
        }

        long riskLevelsSum() {
            return riskLevelValues().reduce(0, Integer::sum);
        }

        Set<Integer> riskLevels() {
            return riskLevelValues().collect(Collectors.toSet());
        }

        private Stream<Integer> riskLevelValues() {
            return lowValues().map(value -> value + 1);
        }

        Set<Integer> lowNumbersSet() {
            return lowValues().collect(Collectors.toSet());
        }

        private Stream<Integer> lowValues() {
            return lowNumbers().map(number -> number.getValue(bingoCard));
        }

        Stream<Number> lowNumbers() {
            return numbers.parallelStream().filter(Number::isLow);
        }

        private Number create(int lineIndex, int columnIndex) {
            int value = bingoCard[lineIndex][columnIndex];
            //log.info("creating value: {}", value);

            Set<AdjacentLocation> adjacentLocations = getAdjacentLocations(lineIndex, columnIndex);
            var isLow = Objects.requireNonNull(adjacentLocations)
                                            .stream()
                                        .allMatch(adjacentLocation -> isValueLowerThanLocation(adjacentLocation, value));

            return new Number(lineIndex, columnIndex, isLow);
        }

        Set<AdjacentLocation> getAdjacentLocations(int lineIndex, int columnIndex) {
            Set<AdjacentLocation> adjacentLocations = new HashSet<>();
            AdjacentLocation up = getOtherPosition(lineIndex, columnIndex, -1, 0);
            AdjacentLocation down = getOtherPosition(lineIndex, columnIndex, 1, 0);
            AdjacentLocation left = getOtherPosition(lineIndex, columnIndex, 0, -1);
            AdjacentLocation right = getOtherPosition(lineIndex, columnIndex, 0, 1);

            return adjacentLocations.addAll(Arrays.asList(up, down, left, right)) ? adjacentLocations : null;
        }

        private AdjacentLocation getOtherPosition(int lineIndex, int columnIndex, int lineDelta, int columnDelta) {
            int line = lineIndex + lineDelta;
            int column = columnIndex + columnDelta;
            boolean isLinePositionInsideTHeArray = line >= 0 && line < this.height;
            boolean isColumnPositionInsideTHeArray = column >= 0 && column < this.width;
            return isLinePositionInsideTHeArray && isColumnPositionInsideTHeArray ? new AdjacentLocation(line, column) : null;
        }

        private Boolean isValueLowerThanLocation(AdjacentLocation adjacentLocation, int value) {
            return Optional.ofNullable(adjacentLocation).map(location -> {
                int cardValue = bingoCard[adjacentLocation.lineIndex][adjacentLocation.columnIndex];
                return value < cardValue;
            }).orElse(true);
        }

        private List<Number> createNumbers() {
            List<Number> numbers = new ArrayList<>();
            for (int lineIndex = 0; lineIndex < this.height; lineIndex++) {
                for (int columnIndex = 0; columnIndex < this.width; columnIndex++) {
                    numbers.add(create(lineIndex, columnIndex));
                }
            }
            return numbers;
        }
    }

    @Getter
    @ToString
    static class Number {
        private final int lineIndex;//int lineIndex, int columnIndex
        private final int columnIndex;
        private final boolean low;

        public Number(int lineIndex, int columnIndex, boolean low) {
            this.lineIndex = lineIndex;
            this.columnIndex = columnIndex;
            this.low = low;
        }

        public int getValue(int[][] bingoCard) {
            return bingoCard[lineIndex][columnIndex];
        }

        public void walk() {

        }

        @AllArgsConstructor
        @ToString
        @Getter
        static class AdjacentLocation {
            private final int lineIndex;
            private final int columnIndex;
        }
    }

    @Override
    public long solvePart02() {
        //Heightmap heightmap = new Heightmap(input);
        //var brol = heightmap.lowNumbers();


        return this.puzzle.size();
    }

    /*
    for (int lineIndex = 0; lineIndex < this.height; lineIndex++) {
            for (int columnIndex = 0; columnIndex < this.width; columnIndex++) {
                int number = getNumber(lineIndex, columnIndex);
                System.out.printf("%5d ", number);
            }
            System.out.println();
        }
     */
}
