package be.bonamis.advent.year2017;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
public class Main {
  public static void main(String[] args) {
    // Initialize the list with values 3 and 8
    List<Integer> numbers = Arrays.asList(3, 8);
    log.info("Initial List: {}", numbers);
    //System.out.println("Initial List: " + numbers);

    // Instance of Main to use the non-static method
    Main mainInstance = new Main();

    // Perform the increment and check operation
    mainInstance.incrementAndCheck(numbers, 3, 5);
  }

  public void incrementAndCheck(List<Integer> numbers, int incrementBy, int divisibleBy) {
    IncrementState initialState = new IncrementState(numbers, 0);
    log.info("Initial State: {}", initialState);

    Optional<IncrementState> result =
            IntStream.range(0, 4)
        //IntStream.iterate(0, i -> i + 1)
            .mapToObj(step -> {
                log.info("step: {}", step);
                return initialState.increment(incrementBy);
            })
            .filter(
                state -> {
                  boolean conditionMet = state.isDivisible(divisibleBy);
                  if (conditionMet) {
                    System.out.println("Step " + state.step() + ": " + state.numbers());
                  }
                  return conditionMet;
                })
            .findFirst();

    if (result.isPresent()) {
      System.out.println(
          "A number in the list is divisible by "
              + divisibleBy
              + " at step "
              + result.get().step());
    } else {
      System.out.println("No number in the list was found to be divisible by " + divisibleBy);
    }
  }

  public record IncrementState(List<Integer> numbers, int step) {

    // Method to increment the list values
    public IncrementState increment(int incrementBy) {
        log.info("increment {} {}", numbers, incrementBy);
      List<Integer> incrementedList = numbers.stream().map(num -> num + incrementBy).toList();
      return new IncrementState(incrementedList, step + 1);
    }

    // Method to check the divisibility condition
    public boolean isDivisible(int divisibleBy) {
        log.info("isDivisible {} {}", numbers, divisibleBy);
      return numbers.stream().anyMatch(num -> num % divisibleBy == 0);
    }
  }
}
