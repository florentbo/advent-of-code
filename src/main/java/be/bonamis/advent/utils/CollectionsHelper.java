package be.bonamis.advent.utils;

import java.util.Collection;

public class CollectionsHelper {
    public static <T extends Comparable<T>> T max(Collection<T> list) {
        return list.stream().max(Comparable::compareTo).orElseThrow();
    }

    public static <T extends Comparable<T>> T min(Collection<T> list) {
        return list.stream().min(Comparable::compareTo).orElseThrow();
    }
}
