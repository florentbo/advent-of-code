package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.FileHelper.getLines;

@Slf4j
public class ShowResult {


	public static void main(String[] args) {
		log.info("Day01 part 01 result: {}", new Day01(getLines("2022_day1_prod.txt")).solvePart01());
		log.info("Day01 part 02 result: {}", new Day01(getLines("2022_day1_prod.txt")).solvePart02());

		log.info("Day02 part 01 result: {}", new Day02(getLines("2022_day2_prod.txt")).solvePart02());
		log.info("Day02 part 02 result: {}", new Day02(getLines("2022_day2_prod.txt")).solvePart02());
    }
}
