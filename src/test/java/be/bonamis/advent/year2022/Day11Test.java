package be.bonamis.advent.year2022;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day11Test {

    private static final String CODE_TXT = "2022/11/2022_11_00_code.txt";

    public static void main(String[] args) {
        List<String> lines = getLines("2022/11/2022_11_input.txt");
        List<Monkey> monkeys = IntStream.range(0, (lines.size() + 1) / 7).mapToObj(i -> Monkey.of(lines, i * 7))
                .toList();
        System.out.println(monkeys);

        long result = getResult(monkeys);
        log.info("Day11 part 01 result: {}", result);

    }

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        List<Monkey> monkeys = IntStream.range(0, (lines.size() + 1) / 7).mapToObj(i -> Monkey.of(lines, i * 7))
                .toList();
        System.out.println(monkeys);
        long result = getResult(monkeys);
        assertThat(result).isEqualTo(10605);
    }

    private static long getResult(List<Monkey> monkeys) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < monkeys.size(); i++) {
            map.put(i, 0);
        }
        for (int round = 1; round <= 20; round++) {

            for (int i = 0; i < monkeys.size(); i++) {
                Monkey monkey = monkeys.get(i);
                List<Integer> startingItems = monkey.startingItems;
                int count = 0;
                for (Integer startingItem : startingItems) {
                    count++;
                    Pair<Integer, Integer> boredAndReceiver = monkey.boredAndReceiver(startingItem);
                    monkeys.get(boredAndReceiver.getSecond()).add(boredAndReceiver.getFirst());
                }
                monkey.clean();
                int newCount = map.get(i) + count;
                map.put(i, newCount);
            }

        }
        System.out.println("report: ");
        for (Monkey monkey : monkeys) {
            System.out.println(monkey.startingItems());
        }
        List<Integer> list = map.values().stream().sorted().toList();
        System.out.println("map: " + list);
        return (long) list.get(list.size() - 1) * list.get(list.size() - 2);
    }

    record Monkey(List<Integer> startingItems, Operation operation, Test test, IfTrue ifTrue, IfFalse ifFalse,
                  long count) {


        private static Monkey of(List<String> lines, int i) {
            String[] items = data(lines.get(i + 1), "Starting items: ").replaceAll(" ", "").split(",");
            List<Integer> startingItems = new ArrayList<>(Arrays.stream(items).map(Integer::parseInt).toList());

            String[] operationData = data(lines.get(i + 2), "Operation: new = old ").split(" ");
            Operation operation = new Operation(operationData[0], operationData[1]);

            int test = Integer.parseInt(data(lines.get(i + 3), "Test: divisible by "));
            int ifTrue = Integer.parseInt(data(lines.get(i + 4), "  If true: throw to monkey "));
            int ifFalse = Integer.parseInt(data(lines.get(i + 5), "  If false: throw to monkey "));

            return new Monkey(startingItems, operation, new Test(test), new IfTrue(ifTrue), new IfFalse(ifFalse), 0);
        }

        public static Monkey withCounter(Monkey monkey, int count) {
            return new Monkey(monkey.startingItems, monkey.operation, monkey.test, monkey.ifTrue, monkey.ifFalse, count);
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
            int getBored = newLevel / 3;
            int test = getBored % divisibleBy;
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

        /*public void inspect() {
            //this.count;
        }*/

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
