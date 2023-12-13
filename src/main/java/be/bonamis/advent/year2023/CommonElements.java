package be.bonamis.advent.year2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonElements {
    public static void main(String[] args) {
        List<String> list = List.of("apple", "banana", "orange", "banana", "grape", "apple");

        Map<String, List<Integer>> elementIndicesMap = findCommonElementsAndIndices(list);

        // Display the common elements and their indices
        for (Map.Entry<String, List<Integer>> entry : elementIndicesMap.entrySet()) {
            String element = entry.getKey();
            List<Integer> indices = entry.getValue();
            System.out.println("Element: " + element + ", Indices: " + indices);
        }
    }

    public static Map<String, List<Integer>> findCommonElementsAndIndices(List<String> list) {
        Map<String, List<Integer>> elementIndicesMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String element = list.get(i);
            elementIndicesMap.computeIfAbsent(element, k -> new ArrayList<>()).add(i);
        }

        elementIndicesMap.entrySet().removeIf(entry -> entry.getValue().size() < 2);

        return elementIndicesMap;
    }
}
