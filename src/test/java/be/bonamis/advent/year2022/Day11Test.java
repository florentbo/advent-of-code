package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day11Test {

    private static final String CODE_TXT = "2022/11/2022_11_00_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/11/2022_11_input.txt");
        //log.info("Day10 part 01 result: {}", new ClockCircuit(lines).run());

    }

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        List<Monkey> monkeys = IntStream.range(0, (lines.size() + 1) / 7).mapToObj(i -> Monkey.of(lines, i * 7))
                .toList();
        System.out.println(monkeys);

        //for (int round = 0; round < 20; round++) {
            for (Monkey monkey : monkeys) {
                List<Integer> startingItems = monkey.startingItems;
                for (Integer startingItem : startingItems) {
                    Pair<Integer, Integer> boredAndReceiver = monkey.boredAndReceiver(startingItem);
                    monkeys.get(boredAndReceiver.getSecond()).add(boredAndReceiver.getFirst());
                }
                monkey.clean();
            }
        //}
        for (Monkey monkey : monkeys) {
            System.out.println(monkey.startingItems());
        }
        assertThat(lines.size()).isEqualTo(27);
    }

    record Monkey(List<Integer> startingItems, Operation operation, Test test, IfTrue ifTrue, IfFalse ifFalse) {

        private static Monkey of(List<String> lines, int i) {
            String[] items = data(lines.get(i + 1), "Starting items: ").replaceAll(" ", "").split(",");
            List<Integer> startingItems = new ArrayList<>(Arrays.stream(items).map(Integer::parseInt).toList());

            String[] operationData = data(lines.get(i + 2), "Operation: new = old ").split(" ");
            Operation operation = new Operation(operationData[0], operationData[1]);

            int test = Integer.parseInt(data(lines.get(i + 3), "Test: divisible by "));
            int ifTrue = Integer.parseInt(data(lines.get(i + 4), "  If true: throw to monkey "));
            int ifFalse = Integer.parseInt(data(lines.get(i + 5), "  If false: throw to monkey "));

            return new Monkey(startingItems, operation, new Test(test), new IfTrue(ifTrue), new IfFalse(ifFalse));
        }

        private static String data(String input, String s) {
            return input.substring(s.length() + 2);
        }

        public int executeOperation(Integer startingItem) {
            int number = this.operation.number.equals("old") ? startingItem : Integer.parseInt(this.operation.number);
            return switch (this.operation.operand) {
                case "+" -> number + startingItem;
                case "-" -> number - startingItem;
                case "*" -> number * startingItem;
                case "/" -> number / startingItem;
                default -> throw new RuntimeException("");
            };
        }

        Pair<Integer, Integer> boredAndReceiver(Integer startingItem) {
            int newLevel = executeOperation(startingItem);
            int divisibleBy = test().divisibleBy();
            int test = newLevel % divisibleBy;
            int getBored = newLevel / 3;
            //System.out.println(getBored);
            int monkeyReceiver;
            if (test == 0) {
                monkeyReceiver = ifTrue.monkeyReceiver;
            } else {
                monkeyReceiver = ifFalse.monkeyReceiver;
            }
            return new Pair<>(getBored, monkeyReceiver);
        }

        public void add(int getBored) {
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
