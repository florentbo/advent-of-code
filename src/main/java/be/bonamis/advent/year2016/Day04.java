package be.bonamis.advent.year2016;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day04 extends TextDaySolver {

  public Day04(InputStream inputStream) {
    super(inputStream);
  }

  public Day04(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    return this.puzzle.stream().map(Room::from).filter(Room::isReal).mapToInt(Room::sectorId).sum();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.stream()
        .map(Room::from)
        .filter(Room::isStoringNorthPole)
        .findFirst()
        .map(Room::sectorId)
        .orElseThrow();
  }

  public record Room(List<String> encryptedName, int sectorId, String checksum) {
    static Room from(String line) {

      int lastDash = line.lastIndexOf('-');
      int firstBracket = line.indexOf('[');

      List<String> encryptedName = Arrays.stream(line.substring(0, lastDash).split("-")).toList();

      int sectorId = Integer.parseInt(line.substring(lastDash + 1, firstBracket));

      String checksum = line.substring(firstBracket + 1, line.length() - 1);
      return new Room(encryptedName, sectorId, checksum);
    }

    List<LetterCount> letterCounts() {
      Stream<String> letters =
          encryptedName.stream().flatMap(s -> s.chars().mapToObj(c -> String.valueOf((char) c)));
      Map<String, Long> letterCounts =
          letters.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      log.debug("letterCounts: {}", letterCounts);
      return letterCounts.entrySet().stream()
          .map(e -> new LetterCount(e.getKey(), e.getValue()))
          .toList();
    }

    List<LetterCount> sortedLetterCounts() {
      return letterCounts().stream().sorted(LetterCount.letterCountComparator).toList();
    }

    boolean isReal() {
      Stream<String> stringStream = sortedLetterCounts().stream().limit(5).map(LetterCount::letter);
      String letters = stringStream.collect(Collectors.joining());
      log.debug("letters {}", letters);

      return letters.equals(this.checksum);
    }

    public record LetterCount(String letter, long count) {

      static Comparator<LetterCount> letterCountComparator =
          Comparator.comparing(LetterCount::count).reversed().thenComparing(LetterCount::letter);
    }

    public String shiftCipherDecrypt() {
      List<String> decrypted = this.encryptedName.stream().map(this::decrypt).toList();
      return String.join(" ", decrypted);
    }

    private String decrypt(String text) {
      IntStream intStream = text.chars().map(c -> decrypt((char) c, this.sectorId));
      return intStream.mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());
    }

    public static char decrypt(char ch, int shift) {
      return (char) (((ch - 'a' + shift % 26) % 26) + 'a');
    }

    boolean isStoringNorthPole() {
      return shiftCipherDecrypt().contains("north");
    }
  }
}
