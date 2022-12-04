package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 extends DaySolver<String> {

    public Day04(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return this.puzzle.stream().map(Elf::new).filter(Elf::oneRangeFullyContainsTheOther).count();
    }

    @Override
    public long solvePart02() {
        return this.puzzle.stream().map(Elf::new).filter(Elf::oneRangeOverLapTheOther).count();
    }

    @Getter
    @ToString
    static class Elf {
        private final Range firstRange;
        private final Range secondRange;

        public Elf(String input) {
            String[] split = input.split(",");
            this.firstRange = new Range(split[0]);
            this.secondRange = new Range(split[1]);
        }

        boolean oneRangeFullyContainsTheOther() {
            return firstRange.range().containsAll(secondRange.range()) ||
                    secondRange.range().containsAll(firstRange.range());
        }

        public boolean oneRangeOverLapTheOther() {
            //Returns true if the two specified collections have no elements in common.
            boolean noElementsInCommon = Collections.disjoint(firstRange.range(), secondRange.range());
            return !noElementsInCommon;
        }

        @Getter
        @ToString
        static class Range {
            private final int start;
            private final int end;

            public Range(String input) {
                String[] split = input.split("-");
                this.start = Integer.parseInt(split[0]);
                this.end = Integer.parseInt(split[1]);
            }

            Set<Integer> range() {
                return IntStream.range(this.start, this.end + 1).boxed().collect(Collectors.toSet());
            }
        }
    }
}
