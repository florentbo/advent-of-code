package be.bonamis.advent.year2017;

import be.bonamis.advent.DayDataRetriever;
import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day04 extends TextDaySolver {

  public Day04(InputStream sample) {
    super(sample);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.stream().filter(this::isPassphraseValid).count();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.stream().filter(this::isPart02PassphraseValid).count();
  }

  public static void main(String[] args) {
    DayDataRetriever.runCode(Day04.class);
  }

  public boolean isPassphraseValid(String input) {
    String[] words = input.split(" ");
    int size = words.length;
    Set<String> set = Arrays.stream(words).collect(Collectors.toSet());
    return size == set.size();
  }

  public boolean isAnagram(String first, String second) {
    return sortCharacters(first).equals(sortCharacters(second));
  }

  private List<Character> sortCharacters(String first) {
    return first.chars().sorted().mapToObj(c -> (char) c).toList();
  }

  public boolean isPart02PassphraseValid(String line) {
    String[] words = line.split(" ");
    int size = words.length;
    Set<List<Character>> sortedWords =
        Arrays.stream(words).map(this::sortCharacters).collect(Collectors.toSet());
    return size == sortedWords.size();
  }
}
