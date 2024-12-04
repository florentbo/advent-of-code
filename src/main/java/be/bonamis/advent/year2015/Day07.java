package be.bonamis.advent.year2015;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;

import be.bonamis.advent.year2015.Day07.Instruction.Operation.Gate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day07 extends TextDaySolver {

  private final List<Instruction> instructions;

  public Day07(InputStream inputStream) {
    super(inputStream);
    this.instructions = getPuzzle().stream().map(Instruction::from).toList();
  }

  public Day07(List<String> puzzle) {
    super(puzzle);
    this.instructions = getPuzzle().stream().map(Instruction::from).toList();
  }

  @Override
  public long solvePart01() {
    Map<String, Integer> run = runInstructions(new HashMap<>());
    boolean containsKey = run.containsKey("a");
    while (!containsKey) {
      run = runInstructions(run);
      log.info("run: {}", run);
      containsKey = run.containsKey("a");
    }
    return run.get("a");
  }

  @Override
  public long solvePart02() {
    return 2024;
  }

  Map<String, Integer> runInstructions(Map<String, Integer> map) {
    for (Instruction instruction : this.instructions) {
      log.debug("map before: {}", map);
      log.debug("instruction={}", instruction);
      instruction
          .operation()
          .gate()
          .ifPresentOrElse(
              gate -> {
                String right = instruction.operation().right();
                Optional<Integer> result = result(instruction, gate, map, right);
                result.ifPresent(value -> map.put(instruction.output(), value));
              },
              () -> {
                Optional<Integer> result = result(instruction);
                result.ifPresent(value -> map.put(instruction.output(), value));
              });
      log.debug("map after: {}", map);
    }
    return map;
  }

  private Optional<Integer> result(Instruction instruction) {
    try {
      return Optional.of(Integer.parseInt(instruction.operation().right()));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private Optional<Integer> result(
      Instruction instruction, Gate gate, Map<String, Integer> map, String right) {
    try {
      int result =
          switch (gate) {
            case AND -> map.get(left(instruction)) & map.get(right);
            case OR -> map.get(left(instruction)) | map.get(right);
            case LSHIFT -> map.get(left(instruction)) << parse(right, map);
            case RSHIFT -> map.get(left(instruction)) >> parse(right, map);
            case NOT -> bitwiseComplement(map.get(right));
          };
      return Optional.of(result);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private int parse(String right, Map<String, Integer> map) {
    try {
      return map.get(right);
    } catch (Exception e) {
      return Integer.parseInt(right);
    }
  }

  private String left(Instruction instruction) {
    return instruction.operation().left().orElseThrow();
  }

  static int bitwiseComplement(int number) {
    return ~number & 0xFFFF;
  }

  /*
      123 -> x
  456 -> y
  x AND y -> d
  x OR y -> e
  x LSHIFT 2 -> f
  y RSHIFT 2 -> g
  NOT x -> h
  NOT y -> i
     */
  public record Instruction(Operation operation, String output) {
    public record Operation(Optional<String> left, Optional<Gate> gate, String right) {
      public enum Gate {
        AND,
        OR,
        LSHIFT,
        RSHIFT,
        NOT
      }

      static Operation of(String right) {
        return new Operation(Optional.empty(), Optional.empty(), right);
      }

      static Operation of(Gate gate, String right) {
        return new Operation(Optional.empty(), Optional.of(gate), right);
      }

      static Operation of(String left, Gate gate, String right) {
        return new Operation(Optional.of(left), Optional.of(gate), right);
      }
    }

    static Instruction of(Operation operation, String output) {
      return new Instruction(operation, output);
    }

    static Instruction from(String input) {
      String[] parts = input.split(" -> ");
      String output = parts[1];
      String[] operation = parts[0].split(" ");
      if (operation.length == 1) {
        return of(Operation.of(operation[0]), output);
      } else if (operation.length == 2) {
        return of(Operation.of(Gate.NOT, operation[1]), output);
      } else {
        return of(Operation.of(operation[0], Gate.valueOf(operation[1]), operation[2]), output);
      }
    }
  }
}
