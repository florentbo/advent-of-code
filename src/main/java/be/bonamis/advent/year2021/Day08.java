package be.bonamis.advent.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Sets;

import be.bonamis.advent.DaySolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day08 extends DaySolver<String> {

	private final List<String[]> outputs;

	public Day08(List<String> puzzle) {
		super(puzzle);
		this.outputs = this.puzzle.stream().map(input -> input.split("\\|")[1].trim().split("\\s+")).collect(Collectors.toList());
	}

	@Override
	public long solvePart01() {
		var digits = this.getOutputs().stream()
				.map(Arrays::asList)
				.flatMap(Collection::stream)
				.map(String::length)
				.collect(Collectors.toList());
		return digits.stream().filter(i -> i == 2 || i == 4 || i == 3 || i == 7).count();
	}

	@Override
	public long solvePart02() {
		return this.puzzle
				.stream()
				.map(line -> line.split("\\|"))
				.map(Line::new)
				.map(line -> getOutPutNumber(line.segments, line.digits)).reduce(0, Integer::sum);
	}

	@AllArgsConstructor
	static class Line {
		private final String[] segments;
		private final String[] digits;

		public Line(String[] lineInput) {
			this.segments = lineInput[0].trim().split("\\s+");
			this.digits = lineInput[1].trim().split("\\s+");
		}
	}

	int getOutPutNumber(String[] inputLine, String[] output) {
		var digits = Stream.of(output).map(s -> getDigit(s, inputLine)).toArray(Integer[]::new);
		return digits[0] * 1000 + digits[1] * 100 + digits[2] * 10 + digits[3];
	}

	int getDigit(String letters, String[] input) {
		final var numbers = getNumbers(input);
		return numbers.keySet().stream().filter(key -> equalIgnoringOrder(key, letters)).findFirst().map(numbers::get).orElse(0);
	}

	private Map<String, Integer> getNumbers(String[] input) {
		// awful code  I know :)

		Map<String, Integer> map = new HashMap<>();

		final var number1 = getUniqueNumber(input, 2);
		map.put(number1, 1);
		final var number4 = getUniqueNumber(input, 4);
		map.put(number4, 4);
		final var number7 = getUniqueNumber(input, 3);
		map.put(number7, 7);
		final var number8 = getUniqueNumber(input, 7);
		map.put(number8, 8);

		final List<String> aLetterAndGLetter = commonChars(Stream.of(input).filter(s -> !(s.equals(number1) || s.equals(number4) || s.equals(number7))).toArray(String[]::new));

		final String cLetterAndFLetter = number1;

		final var number9 = number4 + String.join("", aLetterAndGLetter);
		map.put(number9, 9);

		final var abefg = commonChars(Stream.of(input).filter(s -> (s.length() == 6) && !equalIgnoringOrder(s, number9)).toArray(String[]::new));
		final var abeg = abefg.stream().filter(s -> !cLetterAndFLetter.contains(s)).collect(Collectors.toList());
		final var number0 = number1 + String.join("", abeg);
		map.put(number0, 0);
		final var number6 = Stream.of(input).filter(s -> (s.length() == 6) && !equalIgnoringOrder(s, number0) && !equalIgnoringOrder(s, number9)).toArray(String[]::new)[0];
		map.put(number6, 6);

		final var number8Set = setOf(number8);
		final var number0Set = setOf(number0);

		var letterD = number8Set.stream().filter(s -> !number0Set.contains(s)).collect(Collectors.toList()).get(0);

		Set<String> number3Set = new HashSet<>(setOf(number7));
		number3Set.addAll(setOf(letterD));
		number3Set.addAll(aLetterAndGLetter);
		final var number3 = String.join("", number3Set);
		map.put(number3, 3);

		final var number2AndNumber5 = Stream.of(input).filter(s -> (s.length() == 5) && !equalIgnoringOrder(s, number3)).toArray(String[]::new);

		String number5;
		String number2;
		if (commonChars(new String[]{number2AndNumber5[1], number6}).size() > commonChars(new String[]{number2AndNumber5[0], number6}).size()) {
			number5 = number2AndNumber5[1];
			number2 = number2AndNumber5[0];
		} else {
			number5 = number2AndNumber5[0];
			number2 = number2AndNumber5[1];
		}

		map.put(number2, 2);
		map.put(number5, 5);

		return map;
	}

	private String getUniqueNumber(String[] input, int count) {
		return Stream.of(input).filter(s -> s.length() == count).findFirst().orElse("");
	}

	private static Set<String> setOf(String input) {
		return Stream.of(input.split("")).collect(Collectors.toSet());
	}

	private boolean equalIgnoringOrder(String s1, String s2) {
		return setOf(s1).equals(setOf(s2));
	}

	private static List<String> commonChars(String[] strings) {
		Set<String> intersection = setOf(strings[0]);
		for (int i = 1; i < strings.length; i++) {
			intersection = Sets.intersection(intersection, setOf(strings[i]));
		}
		return new ArrayList<>(intersection);
	}

}
