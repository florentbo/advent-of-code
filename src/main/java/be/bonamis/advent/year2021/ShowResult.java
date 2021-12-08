package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getIntegers;
import static be.bonamis.advent.utils.FileHelper.getLines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShowResult {


	public static void main(String[] args) {
		final var day01Data = getIntegers("2021_day1_prod.txt");
		log.info("Day01 part 01 result: {}", new Day01Another(day01Data).solvePart01());
		log.info("Day01 part 02 result: {}", new Day01Another(day01Data).solvePart02());

		final var day02Data = getLines("2021_day02_prod.txt");
		log.info("Day02 part 01 result: {}", new Day02(day02Data).solvePart01());
		log.info("Day01 part 02 result: {}", new Day02(day02Data).solvePart02());

		final var day03Data = getLines("2021_day03_prod.txt");
		log.info("Day03 part 01 result: {}", new Day03(day03Data).solvePart01());
		log.info("Day03 part 02 result: {}", new Day03(day03Data).solvePart02());

		final var day04Data = getLines("2021_day04_prod.txt");
		log.info("Day04 part 01 result: {}", new Day04(day04Data).solvePart01());
		log.info("Day04 part 02 result: {}", new Day04(day04Data).solvePart02());

		final var day05Data = getLines("2021_day05_prod.txt");
		log.info("Day05 part 01 result: {}", new Day05(day05Data).solvePart01());
		log.info("Day05 part 02 result: {}", new Day05(day05Data).solvePart02());

		final var day06Data = getLines("2021_day06_prod.txt");
		log.info("Day06 part 01 result: {}", new Day06(day06Data).solvePart01());
		log.info("Day06 part 02 result: {}", new Day06(day06Data).solvePart02());

		final var day07Data = getLines("2021_day07_prod.txt");
		log.info("Day07 part 01 result: {}", new Day07(day07Data).solvePart01());
		log.info("Day07 part 02 result: {}", new Day07(day07Data).solvePart02());

    }
}
