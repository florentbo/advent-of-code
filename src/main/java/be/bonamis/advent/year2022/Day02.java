package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Day02 extends DaySolver<String> {

    public Day02(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        List<Round> collect = this.puzzle.stream().map(Round::new).toList();
        for (Round round : collect) {
            System.out.println(round.score());
        }
        return this.puzzle.stream().map(Round::new).map(Round::score).mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public long solvePart02() {
        this.puzzle.stream().map(Round::new).forEach(new Consumer<Round>() {
            @Override
            public void accept(Round round) {

            }
        });
        return 99;
    }


    @Getter
    @ToString
    static class Round {
        private final String opponent;
        private final String you;

        Round(String input) {
            String[] split = input.split(" ");
            this.opponent = split[0];
            this.you = split[1];
        }

        int score() {
            int opponentPosition = opponentPosition();
            int yoursPosition = yoursPosition();
            int operation = (opponentPosition - yoursPosition + 4) % 3 - 1;
            /*System.out.println("opponentPosition: " + opponentPosition);
            System.out.println("yoursPosition: " + yoursPosition);
            System.out.println("oper: " + operation);*/

            if (operation == -1) {
                return yoursPosition + 1 + 6;
            } else if (operation == 1) {
                return yoursPosition + 1;
            }
            return yoursPosition + 1 + 3;
        }

        private int opponentPosition() {
            String[] arrayValue = {"A", "B", "C"};
            return Arrays.asList(arrayValue).indexOf(this.opponent);
        }

        private int yoursPosition() {
            String[] arrayValue = {"X", "Y", "Z"};
            return Arrays.asList(arrayValue).indexOf(this.you);
        }

        int score2() {
            int opponentPosition = opponentPosition();
            int yoursPosition = yoursPosition();
            int operation = yoursPosition - opponentPosition;
            if (operation == 1) {
                return yoursPosition + 1 + 6;
            } else if (operation == -1) {
                return yoursPosition + 1;
            }


            return yoursPosition + 1 + 3;
        }

        private static int handleB() {
            return 1;
        }

        private static int handleC() {
            return 1;
        }
    }
}
