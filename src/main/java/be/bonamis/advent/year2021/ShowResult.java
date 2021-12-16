package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getIntegers;
import static be.bonamis.advent.utils.FileHelper.getLines;

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

		final var day08Data = getLines("2021_day08_prod.txt");
		log.info("Day08 part 01 result: {}", new Day08(day08Data).solvePart01());
		log.info("Day08 part 02 result: {}", new Day08(day08Data).solvePart02());

		final var day11Data = getLines("2021_day11_prod.txt");
		log.info("Day11 part 01 result: {}", new Day11(day11Data).solvePart01());
		log.info("Day11 part 02 result: {}", new Day11(day11Data).solvePart02());

		final var day13Data = getLines("2021_day13_prod.txt");
		log.info("Day13 part 01 result: {}", new Day13(day13Data).solvePart01());
		log.info("Day13 part 02 result: {}", new Day13(day13Data).solvePart02());

		final var day14Data = getLines("2021_day14_prod.txt");
		log.info("Day14 part 01 result: {}", new Day14(day14Data).solvePart01());
		log.info("Day14 part 02 result: {}", new Day14(day14Data).solvePart02());

		final var day15Data = getLines("2021_day15_prod.txt");
		log.info("Day15 part 01 result: {}", new Day15(day15Data).solvePart01());
		log.info("Day15 part 01 result: {}", new Day15(day15Data).solvePart02());
    }
}
