package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

public class Day02 extends DaySolver<String> {

    public Day02(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return this.puzzle.stream().map(Round::new).map(Round::score).mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public long solvePart02() {
        return this.puzzle.stream().map(Round::new).map(Round::score2).mapToInt(Integer::intValue).sum();
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
            int operation = validPosition(yoursPosition - opponentPosition);

            if (operation == 1) {//win
                return yoursPosition + 1 + 6;
            } else if (operation == 0) {
                return yoursPosition + 1 + 3; //draw
            }
            return yoursPosition + 1; // loss
        }

        int score2() {
            int opponentPosition = opponentPosition();
            int positionToReach = yoursPosition();

            if (positionToReach == 2) {//win
                return validPosition(opponentPosition + 1) + 7;
            } else if (positionToReach == 1) {
                return validPosition(opponentPosition) + 4; //draw
            }
            return validPosition(opponentPosition - 1) + 1;  //loss
        }

        private static int validPosition(int calculatedPosition) {
            return (calculatedPosition + 3) % 3;
        }

        private int opponentPosition() {
            String[] arrayValue = {"A", "B", "C"};
            return Arrays.asList(arrayValue).indexOf(this.opponent);
        }

        private int yoursPosition() {
            String[] arrayValue = {"X", "Y", "Z"};
            return Arrays.asList(arrayValue).indexOf(this.you);
        }
    }
}
