package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day24 extends TextDaySolver {

  private final Input input;

  public Day24(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day24(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  record Input(WireValues wireValues, Gates gates) {

    static Input of(List<String> puzzle) {
      int blankLineSeparatorIndex = puzzle.indexOf("");
      log.debug("blankLineSeparatorIndex: {}", blankLineSeparatorIndex);
      List<String> wires = puzzle.subList(0, blankLineSeparatorIndex);
      List<String> gates = puzzle.subList(blankLineSeparatorIndex + 1, puzzle.size());
      return new Input(WireValues.of(wires), Gates.of(gates));
    }

    record WireValues(Map<String, Integer> wires) {
      public static WireValues of(List<String> puzzle) {
        Map<String, Integer> wires = new HashMap<>();
        for (String line : puzzle) {
          log.debug("wire line: {}", line);
          String[] split = line.split(": ");
          wires.put(split[0], Integer.parseInt(split[1]));
        }
        return new WireValues(wires);
      }
    }

    public record Gates(List<Gate> gates) {
      public static Gates of(List<String> gates) {
        return new Gates(gates.stream().map(Gate::of).toList());
      }

      public List<Gate> zValues() {
        Stream<Gate> z = this.gates().stream().filter(gate -> gate.output.startsWith("z"));
        // sort by zvalue reversed
        z = z.sorted(Comparator.comparing(Gate::output).reversed());
        return z.toList();
      }
    }

    public record Gate(String input1, String input2, String output, GateType type) {
      public enum GateType {
        XOR,
        AND,
        OR
      }

      public static Gate of(String line) {
        log.debug("gate line: {}", line);
        // ntg XOR fgs -> mjb
        // regex: ^(\w+) (\w+) (\w+) -> (\w+)$
        Pattern pattern = Pattern.compile("^(\\w+) (\\w+) (\\w+) -> (\\w+)$");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
          String input1 = matcher.group(1);
          GateType type = GateType.valueOf(matcher.group(2));
          String input2 = matcher.group(3);
          String output = matcher.group(4);

          return new Gate(input1, input2, output, type);
        }
        return null;
      }
    }
  }

  private int outputBit(Input.Gate gate, Map<String, Integer> wireValues) {
    Integer input01 = wireValues.get(gate.input1());
    Integer input02 = wireValues.get(gate.input2());
    return switch (gate.type()) {
      case XOR -> input01 ^ input02;
      case AND -> input01 & input02;
      case OR -> input01 | input02;
    };
  }

  @Override
  public long solvePart01() {
    Input.Gates gates = this.input.gates();
    var zValues = gates.zValues();

    log.debug("zValues: {}", zValues);

    var zValuesInputs =
        zValues.stream().flatMap(gate -> Stream.of(gate.input1, gate.input2)).toList();
    log.debug("zValuesInputs: {}", zValuesInputs);
    var wireValues = this.input.wireValues().wires;
    boolean allZValuesHaveWireValue = allZValuesHaveWireValue(zValuesInputs, wireValues);
    log.debug("allZValuesHaveWireValue: {}", allZValuesHaveWireValue);
    while (!allZValuesHaveWireValue) {
      for (Input.Gate gate : gates.gates()) {
        log.debug("gate in the test loop: {}", gate);
        if (!wireValues.containsKey(gate.output)
            && (wireValues.containsKey(gate.input1)
                && wireValues.containsKey(gate.input2))) {
          int s = outputBit(gate, wireValues);
          wireValues.put(gate.output, s);
          log.debug("wireValuesCopy put: {} value: {}", gate.output, s);
        }
      }
      allZValuesHaveWireValue = allZValuesHaveWireValue(zValuesInputs, wireValues);
      log.debug("allZValuesHaveWireValue after loop: {}", allZValuesHaveWireValue);
    }
    String joined =
        zValues.stream()
            .map(gate -> String.valueOf(outputBit(gate, wireValues)))
            .collect(Collectors.joining());
    log.debug("joined: {}", joined);

    long result = Long.parseLong(joined, 2);
    log.debug("result: {}", result);

    return result;
  }

  boolean allZValuesHaveWireValue(List<String> zValuesInputs, Map<String, Integer> wireValues) {
    return zValuesInputs.stream().allMatch(wireValues::containsKey);
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
