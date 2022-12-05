package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.*;

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

    record Elf(Range firstRange, Range secondRange) {

        public Elf(String input) {
            this(new Range(input.split(",")[0]), new Range(input.split(",")[1]));
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

        record Range(int start, int end) {
            public Range(String input) {
                this(parseInt(input.split("-")[0]), parseInt(input.split("-")[1]));
            }

            Set<Integer> range() {
                return IntStream.range(this.start, this.end + 1).boxed().collect(Collectors.toSet());
            }
        }
    }
}
