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
    return runInstructions().get("a");
  }

  @Override
  public long solvePart02() {
    return 2024;
  }

  Map<String, Integer> runInstructions() {
    Map<String, Integer> map = new HashMap<>();
    for (Instruction instruction : this.instructions) {
      log.info("map before: {}", map);
      log.info("instruction={}", instruction);
      instruction
          .operation()
          .gate()
          .ifPresentOrElse(
              gate -> {
                String right = instruction.operation().right();
                int result =
                    switch (gate) {
                      case AND -> map.get(left(instruction)) & map.get(right);
                      case OR -> map.get(left(instruction)) | map.get(right);
                      case LSHIFT -> map.get(left(instruction)) << Integer.parseInt(right);
                      case RSHIFT -> map.get(left(instruction)) >> Integer.parseInt(right);
                      case NOT -> bitwiseComplement(map.get(right));
                    };
                map.put(instruction.output(), result);
              },
              () -> {
                int result = Integer.parseInt(instruction.operation().right());
                map.put(instruction.output(), result);
              });
      log.info("map after: {}", map);
    }
    return map;
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
