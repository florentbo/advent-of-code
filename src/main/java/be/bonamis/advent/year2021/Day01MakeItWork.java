package be.bonamis.advent.year2021;

import java.util.ArrayList;
import java.util.List;

import be.bonamis.advent.DaySolver;

public class Day01MakeItWork extends DaySolver<Integer> {

	public Day01MakeItWork(List<Integer> puzzle) {
		super(puzzle);
	}

	@Override
	public long solvePart01() {
		return getMeasurementIncreases(puzzle);
	}

	private long getMeasurementIncreases(List<Integer> puzzle) {
		List<Tuple> tuples = new ArrayList<>();
		for (int i = 1; i < puzzle.size(); i++) {
			tuples.add(new Tuple(puzzle.get(i - 1), puzzle.get(i)));
		}
		return tuples
				.stream()
				.filter(tuple -> tuple.first < tuple.second)
				.count();
	}

	@Override
	public long solvePart02() {
		List<Integer> tuples = new ArrayList<>();
		for (int i = 2; i < puzzle.size(); i++) {
			tuples.add(puzzle.get(i - 2) +  puzzle.get(i - 1) +  puzzle.get(i));
		}
		return getMeasurementIncreases(tuples);
	}

	static class Tuple {
		int first;
		int second;

		public Tuple(int first, int second) {
			this.first = first;
			this.second = second;
		}
	}
}
