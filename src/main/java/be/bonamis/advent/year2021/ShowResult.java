package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getIntegers;
import static be.bonamis.advent.utils.FileHelper.getLines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowResult {

	private static final Logger logger = LoggerFactory.getLogger(ShowResult.class);

	public static void main(String[] args) {
		final var day01Data = getIntegers("2021_day1_prod.txt");
		logger.info("Day01 part 01 result: {}", new Day01Another(day01Data).solvePart01());
		logger.info("Day01 part 02 result: {}", new Day01Another(day01Data).solvePart02());

		final var day02Data = getLines("2021_day02_prod.txt");
		logger.info("Day02 part 01 result: {}", new Day02(day02Data).solvePart01());
		logger.info("Day01 part 02 result: {}", new Day02(day02Data).solvePart02());

		final var day03Data = getLines("2021_day03_prod.txt");
		logger.info("Day03 part 01 result: {}", new Day03(day03Data).solvePart01());
		logger.info("Day03 part 02 result: {}", new Day03(day03Data).solvePart02());

		final var day04Data = getLines("2021_day04_prod.txt");
		logger.info("Day04 part 01 result: {}", new Day04(day04Data).solvePart01());
		logger.info("Day04 part 02 result: {}", new Day04(day04Data).solvePart02());

		final var day05Data = getLines("2021_day05_prod.txt");
		logger.info("Day05 part 01 result: {}", new Day05(day05Data).solvePart01());
		logger.info("Day05 part 02 result: {}", new Day05(day05Data).solvePart02());

		final var day06Data = getLines("2021_day06_prod.txt");
		logger.info("Day06 part 01 result: {}", new Day06(day06Data).solvePart01());
		logger.info("Day06 part 02 result: {}", new Day06(day06Data).solvePart02());

    }
}
