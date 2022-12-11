package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.*;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day11Test {

    private static final String CODE_TXT = "2022/11/2022_11_00_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/11/2022_11_input.txt");
        long result = solvePart01(lines);
        log.info("Day11 part 01 result: {}", result);
    }

    @Test
    @Disabled
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        long result = solvePart01(lines);
        assertThat(result).isEqualTo(10605);
    }

    @Test
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(solvePart02(lines, 1)).isEqualTo(24);
        assertThat(solvePart02(lines, 20)).isEqualTo(103 * 99);
        assertThat(solvePart02(lines, 1000)).isEqualTo(5204 * 5192);
        assertThat(solvePart02(lines, 5000)).isEqualTo(26000 * 26075);
    }

    private static long solvePart01(List<String> lines) {
        List<Monkey> monkeys = IntStream.range(0, (lines.size() + 1) / 7).mapToObj(i -> Monkey.of(lines, i * 7))
                .toList();
        //System.out.println(monkeys);
        return getResult(monkeys, 20, true);
    }

    private static long solvePart02(List<String> lines, int numberOfRound) {
        List<Monkey> monkeys = IntStream.range(0, (lines.size() + 1) / 7).mapToObj(i -> Monkey.of(lines, i * 7))
                .toList();
        //System.out.println(monkeys);
        return getResult(monkeys, numberOfRound, false);
    }

    static class MonkeyCounter {
        private final Monkey monkey;
        long counter = 0;

        MonkeyCounter(Monkey monkey) {
            this.monkey = monkey;
        }

        public long getCounter() {
            return counter;
        }
    }

    private static long getResult(List<Monkey> monkeys, int numberOfRound, boolean worryLevelToBeDividedByThree) {
        List<MonkeyCounter> monkeyCounters = monkeys.stream().map(MonkeyCounter::new).toList();
        for (int round = 1; round <= numberOfRound; round++) {
            for (MonkeyCounter monkeyCounter : monkeyCounters) {
                Monkey monkey = monkeyCounter.monkey;
                List<Long> startingItems = monkey.startingItems;
                for (Long startingItem : startingItems) {
                    Pair<Long, Integer> boredAndReceiver = monkey.boredAndReceiver(startingItem, worryLevelToBeDividedByThree);
                    monkeys.get(boredAndReceiver.getSecond()).add(boredAndReceiver.getFirst());
                }
                monkeyCounter.counter += startingItems.size();
                monkey.clean();
            }
        }
        /*System.out.println("report: ");
        for (Monkey monkey : monkeys) {
            System.out.println(monkey.startingItems());
        }*/
        System.out.println("unsorted list: " + monkeyCounters.stream().map(MonkeyCounter::getCounter).toList());
        List<Long> list = monkeyCounters.stream().map(MonkeyCounter::getCounter).sorted().toList();
        //System.out.println("sorted list: " + list);
        return (long) list.get(list.size() - 1) * list.get(list.size() - 2);
    }

    record Monkey(List<Long> startingItems, Operation operation, Test test, IfTrue ifTrue, IfFalse ifFalse,
                  long count) {


        private static Monkey of(List<String> lines, int i) {
            String[] items = data(lines.get(i + 1), "Starting items: ").replaceAll(" ", "").split(",");
            List<Long> startingItems = new ArrayList<>(Arrays.stream(items).map(Long::parseLong).toList());

            String[] operationData = data(lines.get(i + 2), "Operation: new = old ").split(" ");
            Operation operation = new Operation(operationData[0], operationData[1]);

            int test = Integer.parseInt(data(lines.get(i + 3), "Test: divisible by "));
            int ifTrue = Integer.parseInt(data(lines.get(i + 4), "  If true: throw to monkey "));
            int ifFalse = Integer.parseInt(data(lines.get(i + 5), "  If false: throw to monkey "));

            return new Monkey(startingItems, operation, new Test(test), new IfTrue(ifTrue), new IfFalse(ifFalse), 0);
        }

        private static String data(String input, String s) {
            return input.substring(s.length() + 2);
        }

        public long executeOperation(Long startingItem) {
            long number = this.operation.number.equals("old") ? startingItem : Integer.parseInt(this.operation.number);
            return switch (this.operation.operand) {
                case "+" -> number + startingItem;
                case "-" -> number - startingItem;
                case "*" -> number * startingItem;
                case "/" -> number / startingItem;
                default -> throw new RuntimeException("operand not found");
            };
        }

        Pair<Long, Integer> boredAndReceiver(Long startingItem, boolean worryLevelToBeDividedByThree) {
            long newLevel = executeOperation(startingItem);
            long getBored = worryLevelToBeDividedByThree ? newLevel / 3 : newLevel;
            int monkeyReceiver;
            if (getBored % this.test().divisibleBy() == 0) {
                monkeyReceiver = ifTrue.monkeyReceiver;
            } else {
                monkeyReceiver = ifFalse.monkeyReceiver;
            }
            return new Pair<>(getBored, monkeyReceiver);
        }

        public void add(long getBored) {
            this.startingItems.add(getBored);
        }

        public void clean() {
            this.startingItems.clear();
        }

        record Operation(String operand, String number) {
        }

        record Test(int divisibleBy) {
        }

        record IfTrue(int monkeyReceiver) {
        }

        record IfFalse(int monkeyReceiver) {
        }
    }
}
