package be.bonamis.advent.year2021;

import static be.bonamis.advent.year2021.Day04.Board.createBoards;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import be.bonamis.advent.DaySolver;
import lombok.Getter;

public class Day04 extends DaySolver<String> {

	private final List<Integer> numbers;
	private final List<Board> boards;

	public Day04(List<String> puzzle) {
		super(puzzle);
		this.numbers = Stream.of(this.puzzle.get(0).split(",")).map(Integer::parseInt).collect(toList());
		this.boards = createBoards(this.puzzle);
	}

	@Override
	public long solvePart01() {
		Set<Integer> drawnNumbers = new HashSet<>();
		for (Integer number : this.numbers) {
			drawnNumbers.add(number);
			List<Long> winningBoard = findWinningBoard(drawnNumbers, number);

			if (!winningBoard.isEmpty()) {
				return winningBoard.get(0);
			}
		}
		return 0;
	}

	private List<Long> findWinningBoard(Set<Integer> drawnNumbers, Integer number) {
		return this.boards
				.stream()
				.filter(board -> board.hasNotYetWin() &&board.contains(drawnNumbers) > 0).map(board -> {
					board.hasWin();
					final var integer = board.unmarkedNumbers(drawnNumbers);
					return (long) integer * number;
				}).collect(toList());
	}

	@Override
	public long solvePart02() {
		Set<Integer> drawnNumbers = new HashSet<>();
		Long result = 0L;
		for (Integer number : this.numbers) {
			drawnNumbers.add(number);
			var winningBoard = findWinningBoard(drawnNumbers, number);
			if (!winningBoard.isEmpty()) {
				result = winningBoard.get(winningBoard.size() - 1);
			}
		}
		return result;
	}

	@Getter
	static class Board {
		private final List<Line> rows;
		private final List<Line> columns;
		private boolean isWinning;

		public Board(int[][] bingoCard) {
			this.rows = Arrays.stream(bingoCard)
					.map(row -> new Line(Arrays.stream(row).boxed().collect(toList())))
					.collect(toList());

			this.columns = createColumns(bingoCard);
		}

		boolean hasNotYetWin(){
			return !this.isWinning;
		}

		void hasWin(){
			this.isWinning= true;
		}

		private List<Line> createColumns(int[][] bingoCard) {
			List<Line> lines = new ArrayList<>();
			for (int i = 0; i < bingoCard[0].length; i++) {
				List<Integer> column = new ArrayList<>();
				for (int[] ints : bingoCard) {
					column.add(ints[i]);
				}
				lines.add(new Line(column));
			}
			return lines;
		}

		static List<Board> createBoards(List<String> puzzle) {
			List<Board> boardToCreate = new ArrayList<>();
			for (int i = 2; i < puzzle.size(); i += 6) {
				int end = i + 5;
				final var bingoCard = IntStream
						.range(i, end)
						.mapToObj(puzzle::get).map(l -> l.trim().split("\\s+"))
						.map(sa -> Stream.of(sa).mapToInt(Integer::parseInt).toArray())
						.toArray(int[][]::new);
				boardToCreate.add(new Board(bingoCard));
			}
			return boardToCreate;
		}

		public Integer contains(Set<Integer> drawnNumbers) {
			Optional<Line> first = findLine(drawnNumbers);
			return first.map(line -> line.getNumbers().stream().reduce(0, Integer::sum)).orElse(0);
		}

		private Optional<Line> findLine(Set<Integer> drawnNumbers) {
			var findRow = findLineContainingAllDrawnNumbers(drawnNumbers, this.rows);
			return findRow.isPresent() ? findRow : findLineContainingAllDrawnNumbers(drawnNumbers, this.columns);
		}

		private Optional<Line> findLineContainingAllDrawnNumbers(Set<Integer> drawnNumbers, List<Line> rows) {
			return rows.stream().filter(line -> drawnNumbers.containsAll(line.getNumbers())).findFirst();
		}

		public Integer unmarkedNumbers(Set<Integer> drawnNumbers) {
			return this.rows.stream()
					.flatMap(line -> line.getNumbers().stream())
					.filter(number -> !drawnNumbers.contains(number))
					.reduce(0, Integer::sum);
		}

		@Getter
		static class Line {
			private final List<Integer> numbers;

			Line(List<Integer> numbers) {
				this.numbers = numbers;
			}
		}
	}
}
