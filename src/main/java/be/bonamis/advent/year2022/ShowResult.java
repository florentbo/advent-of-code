package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.FileHelper.getLines;

@Slf4j
public class ShowResult {


	public static void main(String[] args) {
		//log.info("Day01 part 01 result: {}", new Day01(getLines("2022_day1_prod.txt")).solvePart01());
		//log.info("Day01 part 02 result: {}", new Day01(getLines("2022_day1_prod.txt")).solvePart02());

		log.info("Day02 part 01 result: {}", new Day02(getLines("2022_day02_prod.txt")).solvePart01());
		log.info("Day02 part 02 result: {}", new Day02(getLines("2022_day02_prod.txt")).solvePart02());

		log.info("Day03 part 01 result: {}", new Day03(getLines("2022_day03_prod.txt")).solvePart01());
		log.info("Day03 part 02 result: {}", new Day03(getLines("2022_day03_prod.txt")).solvePart02());

		log.info("Day04 part 01 result: {}", new Day04(getLines("2022/04/2022_04_input.txt")).solvePart01());
		log.info("Day04 part 02 result: {}", new Day04(getLines("2022/04/2022_04_input.txt")).solvePart02());

		log.info("Day05 part 01 result: {}", new Day05(getLines("2022/05/2022_05_input.txt")).solvePart01String());
		//log.info("Day05 part 02 result: {}", new Day04(getLines("2022/05/2022_05_input.txt")).solvePart02());

		log.info("Day04 part 01 result: {}", new Day07(getLines("2022/07/2022_07_input.txt")).solvePart01());

		log.info("Day08 part 01 result: {}", new Day08(getLines("2022/08/2022_08_input.txt")).solvePart01());
		log.info("Day08 part 02 result: {}", new Day08(getLines("2022/08/2022_08_input.txt")).solvePart02());

		log.info("Day09 part 01 result: {}", new Day09(getLines("2022/09/2022_09_input.txt")).solvePart01());

		//log.info("Day10 part 01 result: {}", new Day09(getLines("2022/09/2022_09_input.txt")).solvePart01());
	}
}
