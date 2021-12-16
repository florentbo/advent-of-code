package be.bonamis.advent.year2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import be.bonamis.advent.DaySolver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day14 extends DaySolver<String> {

	private final String polymerTemplate;
	private final Map<String, String> rules;

	public Day14(List<String> puzzle) {
		super(puzzle);
		this.polymerTemplate = puzzle.get(0);
		this.rules = puzzle.stream().skip(2).map(s -> s.split(" -> "))
				.collect(Collectors.toMap(rule -> rule[0], rule -> rule[1]));
	}

	@Override
	public long solvePart01() {
		return solve(10);
	}

	@Override
	public long solvePart02() {
		return solve(40);
	}

	private long solve(int steps) {
		final var steps2 = steps(steps);
		log.info("steps2: {}", steps2);
		final var count = count(steps2);
		log.info("count: {}", count);
		return count.values().stream().max(Long::compareTo).orElseThrow() - count.values().stream().min(Long::compareTo).orElseThrow();
	}

	Map<String, Long> steps(int steps) {
		var split = this.polymerTemplate.split("");
		Map<String, Long> counter = new HashMap<>();
		for (int i = 0; i < split.length - 1; i++) {
			String pair = split[i] + split[i + 1];
			final var value = counter.get(pair);
			if (value != null) {
				counter.put(pair, value + 1);
			} else {
				counter.put(pair, 1L);
			}
		}

		log.info("counter in step result after init: {}", counter);

		for (int step = 1; step <= steps; step++) {
			Map<String, Long> tempCounter = new HashMap<>();
			for (Map.Entry<String, Long> entry : counter.entrySet()) {
				final var key = entry.getKey();
				final var ruleValue = rules.get(key);

				final var keyCharacters = key.split("");
				final var key1 = keyCharacters[0] + ruleValue;
				final var key2 = ruleValue + keyCharacters[1];

				final var val = entry.getValue();

				tempCounter.merge(key1, val, Long::sum);
				tempCounter.merge(key2, val, Long::sum);
			}
			log.info("tempCounter in step result: {}", tempCounter);
			counter = tempCounter;
		}
		return counter;
	}

	Map<Character, Long> count(Map<String, Long> counter) {
		Multimap<Character, Long> map = ArrayListMultimap.create();
		log.info("counter in count2: {}", counter);
		for (Map.Entry<String, Long> entry : counter.entrySet()) {
			final var key = entry.getKey();

			final var value = entry.getValue();
			if (value > 0) {
				final var first = key.charAt(0);
				map.put(first, value);
			}
		}
		map.put(this.polymerTemplate.charAt(this.polymerTemplate.length()-1), 1L);//adding the last character
		return map.asMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
				entry.getValue().stream().reduce(0L, Long::sum)));
	}

	String naiveSteps(int steps) {
		String result = naiveResult(this.polymerTemplate);
		for (int step = 2; step <= steps; step++) {
			result = naiveResult(result);
		}
		return result;
	}

	Map<Character, Integer> naiveCount(String sequence) {
		Map<Character, Integer> count = new HashMap<>();
		char c;
		for (c = 'A'; c <= 'Z'; ++c) {
			count.put(c, CharMatcher.is(c).countIn(sequence));
		}
		return count.entrySet().stream().filter(entry -> entry.getValue() > 0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	String naiveResult(String input) {
		var split = input.split("");
		return IntStream
					   .range(0, split.length - 1)
					   .mapToObj(i -> split[i] + split[i + 1])
					   .map(s -> s.split("")[0] + rules.get(s)).collect(Collectors.joining("")) + split[split.length - 1];
	}
}
