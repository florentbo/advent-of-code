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
    }
}
