package be.bonamis.advent;

import java.util.Optional;

public interface Day<T> {

	long solvePart01();
	long solvePart02();
	default Optional<String> solvePart02String() { return Optional.empty(); }
}
