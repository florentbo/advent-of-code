package be.bonamis.advent.year2021;

//import java.awt.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Grid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day20 extends DaySolver<String> {

	private final List<Integer> imageEnhancementAlgorithm;

	private final Grid grid;

	public Day20(List<String> puzzle) {
		super(puzzle);
		final ToIntFunction<String> parseInt = value -> value.equals("#") ? 1 : 0;
		this.imageEnhancementAlgorithm = Stream.of(this.puzzle.get(0).split("")).mapToInt(parseInt).boxed().collect(Collectors.toList());
		this.grid = new Grid(puzzle.parallelStream().skip(2).map(l -> l.trim().split(""))
									 .map(sa -> Stream.of(sa).mapToInt(parseInt).toArray())
									 .toArray(int[][]::new));
	}

	@Override
	public long solvePart01() {
		/*
		apply the image enhancement algorithm twice
		 */

		return puzzle.size();
	}

	@Override
	public long solvePart02() {
		return puzzle.size() + 1;
	}

	@AllArgsConstructor
	static class InputImage {
		private final Grid grid;
		private final Set<Point> lightPixels;
	}
}

