package be.bonamis.advent.year2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import be.bonamis.advent.DaySolver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day06 extends DaySolver<String> {

	public Day06(List<String> puzzle) {
		super(puzzle);
	}

	void updateList(List<Integer> fishes, int times) {
		for (int i = 0; i < times; i++) {
			final var newLanternFishCount = fishes.parallelStream().filter(timer -> timer == 0).count();
			ListIterator<Integer> iterator = fishes.listIterator();
			while (iterator.hasNext()) {
				Integer timer = iterator.next();
				if (timer > 0) {
					iterator.set(timer - 1);
				} else {
					iterator.set(6);
				}
			}
			for (long ii = 0; ii < newLanternFishCount; ii++) {
				fishes.add(8);
			}
		}
	}

	@Override
	public long solvePart01() {
		List<Integer> fishes = Arrays.stream(this.puzzle.get(0).trim().split(","))
				.map(Integer::parseInt).collect(Collectors.toList());
		updateList(fishes, 80);
		return fishes.size();
	}

	@Override
	public long solvePart02() {
		List<Integer> fishes = Arrays.stream(this.puzzle.get(0).trim().split(","))
				.map(Integer::parseInt).collect(Collectors.toList());

		Map<Integer, Long> map = initResult();
		for (Integer fish : fishes) {
			final var value = map.get(fish);
			map.put(fish, value + 1);
		}

		final var updateFishes = updateMap(map, 256);

		return updateFishes.values().stream().reduce(0L, Long::sum);
	}

	Map<Integer, Long> updateMap(Map<Integer, Long> fishes, int times) {
		for (int i = 0; i < times; i++) {
			final var result = initResult();
			for (Map.Entry<Integer, Long> entry : fishes.entrySet()) {
				final var timer = entry.getKey();
				final var value = entry.getValue();
				if (value > 0) {
					if (timer > 0) {
						int newTimer = timer - 1;
						result.put(newTimer, result.get(newTimer) + value);
					} else {
						result.put(6, value);
						result.put(8, value);
					}
				}
			}
			fishes = result;
		}
		return fishes;
	}

	static Map<Integer, Long> initResult() {
		Map<Integer, Long> map = new HashMap<>();
		map.put(0, 0L);
		map.put(1, 0L);
		map.put(2, 0L);
		map.put(3, 0L);
		map.put(4, 0L);
		map.put(5, 0L);
		map.put(6, 0L);
		map.put(7, 0L);
		map.put(8, 0L);
		return map;
	}
}
