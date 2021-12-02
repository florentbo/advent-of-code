package be.bonamis.advent.year2021;

import java.util.List;
import java.util.stream.Collectors;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.year2021.Day02.Move.Direction;

public class Day02 extends DaySolver<String> {

	public Day02(List<String> puzzle) {
		super(puzzle);
	}

	@Override
	public long solvePart01() {
		long horizontalPosition = 0;
		long depth = 0;

		for (Move move : getMoves()) {
			int units = move.getUnits();
			Direction dir = move.getDirection();
			switch (dir) {
			case FORWARD:
				horizontalPosition += units;
				break;

			case DOWN:
				depth += units;
				break;

			case UP:
				depth -= units;
				break;
			}
		}
		return horizontalPosition * depth;
	}

	private List<Move> getMoves() {
		return this.puzzle
				.stream()
				.map(Move::new).collect(Collectors.toList());
	}

	@Override
	public long solvePart02() {

		long horizontalPosition = 0;
		long depth = 0;
		long aim = 0;

		for (Move move : getMoves()) {
			int units = move.getUnits();
			Direction dir = move.getDirection();
			switch (dir) {
			case FORWARD:
				horizontalPosition += units;
				depth += aim * units;
				break;

			case DOWN:
				aim += units;
				break;

			case UP:
				aim -= units;
				break;
			}
		}
		return horizontalPosition * depth;
	}

	static class Move {
		private final Direction direction;
		private final int units;

		public Move(String line) {
			String[] split = line.split("\\s+");
			this.direction = Direction.valueOf(split[0].toUpperCase());
			this.units = Integer.parseInt(split[1]);
		}

		public Direction getDirection() {
			return direction;
		}

		public int getUnits() {
			return units;
		}

		enum Direction {
			UP,
			DOWN,
			FORWARD
		}

	}
}
