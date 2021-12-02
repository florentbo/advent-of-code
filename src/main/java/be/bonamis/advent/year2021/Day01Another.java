package be.bonamis.advent.year2021;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.List;
import java.util.stream.IntStream;

import be.bonamis.advent.DaySolver;

public class Day01Another extends DaySolver<Integer> {

	public Day01Another(List<Integer> puzzle) {
		super(puzzle);
	}

	@Override
	public long solvePart01() {
		return getMeasurementIncreases(this.puzzle);
	}

	private long getMeasurementIncreases(List<Integer> puzzle) {
		return IntStream.range(1, puzzle.size())
				.filter(i -> puzzle.get(i) > puzzle.get(i - 1))
				.count();
	}

	@Override
	public long solvePart02() {
		List<Integer> sums = IntStream
								.range(2, puzzle.size())
								.mapToObj(i -> puzzle.get(i - 2) + puzzle.get(i - 1) + puzzle.get(i))
								.collect(toUnmodifiableList());
		return getMeasurementIncreases(sums);
	}
}
