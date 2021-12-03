package be.bonamis.advent.year2021;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.bonamis.advent.DaySolver;

public class Day03 extends DaySolver<String> {

	private final int length;

	public Day03(List<String> puzzle) {
		super(puzzle);
		this.length = this.puzzle.get(0).length();
	}

	static class CommonBit {
		private final String most;
		private final String least;

		CommonBit(int most, int least) {
			this.most = String.valueOf(most);
			this.least = String.valueOf(least);
		}

		public String getMost() {
			return most;
		}

		public String getLeast() {
			return least;
		}
	}

	@Override
	public long solvePart01() {
		List<CommonBit> list = IntStream.range(0, length)
				.mapToObj(i -> {
					final var result = getColumnResult(i, this.puzzle);
					return new CommonBit(result, invertResult(result));})
				.collect(Collectors.toList());

		long gamma = commonBitsBinary(list, CommonBit::getMost);
		long epsilon = commonBitsBinary(list, CommonBit::getLeast);

		return gamma * epsilon;
	}

	private int commonBitsBinary(List<CommonBit> list, Function<CommonBit, String> getMost) {
		return getBinaryFromString(commonBits(list, getMost));
	}

	private String commonBits(List<CommonBit> list, Function<CommonBit, String> getMost) {
		return list.stream().map(getMost).collect(Collectors.joining(""));
	}

	@Override
	public long solvePart02() {
		return getPuzzleResult(true) * getPuzzleResult(false);
	}

	private int getBinaryFromString(String string) {
		return Integer.parseInt(string, 2);
	}

	private int getColumnResult(int columnIndex, List<String> puzzle) {
		final int sum = puzzle
				.stream()
				.map(line -> Integer.parseInt(String.valueOf(line.charAt(columnIndex))))
				.reduce(0, Integer::sum);

		return (sum >= (puzzle.size() - sum)) ? 1 : 0;
	}

	public int invertResult(int result) {
		return result^1;
	}

	private long getPuzzleResult(boolean more) {
		List<String> oneBitPuzzle = new ArrayList<>(this.puzzle);

		int i = 0;
		while (i < this.length && oneBitPuzzle.size() > 1) {
			final var columnResult = getColumnResult(i, oneBitPuzzle);
			int result = more ? columnResult : invertResult(columnResult);
			int columnIndex = i;
			oneBitPuzzle.removeIf(line -> Integer.parseInt(String.valueOf(line.charAt(columnIndex))) != result);
			i++;
		}
		return getBinaryFromString(oneBitPuzzle.get(0));
	}
}
