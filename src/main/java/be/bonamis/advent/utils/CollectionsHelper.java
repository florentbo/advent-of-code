package be.bonamis.advent.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionsHelper {
    public static <T extends Comparable<T>> T max(Collection<T> list) {
        return list.stream().max(Comparable::compareTo).orElseThrow();
    }

    public static <T extends Comparable<T>> T min(Collection<T> list) {
        return list.stream().min(Comparable::compareTo).orElseThrow();
    }

    public static int sum(Stream<Integer> stream) {
        return stream.reduce(Integer::sum).orElseThrow();
    }
}
