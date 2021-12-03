package be.bonamis.advent.year2021;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

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

		CommonBit(String most, String least) {
			this.most = most;
			this.least = least;
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
		List<CommonBit> list = new ArrayList<>();

		for (int i = 0; i < length; i++) {
			final var result = getColumnResult(i, this.puzzle);
			list.add(new CommonBit(result, invertResult(result)));
		}

		long gamma = getBinaryFromString(getBinaryString(list.stream().map(CommonBit::getMost).collect(toList())));
		long epsilon = getBinaryFromString(getBinaryString(list.stream().map(CommonBit::getLeast).collect(toList())));

		return gamma * epsilon;
	}

	@Override
	public long solvePart02() {
		return getPuzzleResult(true) * getPuzzleResult(false);
	}

	private int getBinaryFromString(String string) {
		return Integer.parseInt(string, 2);
	}

	private String getColumnResult(int columnIndex, List<String> puzzle) {
		final int sum = puzzle
				.stream()
				.map(line -> Integer.parseInt(String.valueOf(line.charAt(columnIndex))))
				.reduce(0, Integer::sum);

		return (sum >= (puzzle.size() - sum)) ? "1" : "0";
	}

	public String invertResult(String resu) {
		return resu.equals("0") ? "1" : "0";
	}

	private String getBinaryString(List<String> list) {
		return list.stream()
				.map(String::valueOf)
				.collect(joining());
	}

	private long getPuzzleResult(boolean more) {
		List<String> oneBitPuzzle = new ArrayList<>(this.puzzle);
		int i = 0;
		while (i < this.length && oneBitPuzzle.size() > 1) {
			final var columnResult = getColumnResult(i, oneBitPuzzle);
			String result = more ? columnResult : invertResult(columnResult);
			int columnIndex = i;
			oneBitPuzzle.removeIf(line -> line.charAt(columnIndex) != result.charAt(0));
			i++;
		}
		return getBinaryFromString(oneBitPuzzle.get(0));
	}
}
