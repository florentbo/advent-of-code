package be.bonamis.advent;

public interface Day<T> {

	long solvePart01();
	long solvePart02();
	default String solvePart02String() { return ""; }
}
