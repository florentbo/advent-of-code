package be.bonamis.advent.year2021;

import java.util.List;

import be.bonamis.advent.DaySolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day21 extends DaySolver<String> {

    private final int positionPlayer1;
    private final int positionPlayer2;

    public Day21(List<String> puzzle) {
        super(puzzle);
        positionPlayer1 = Integer.parseInt(this.puzzle.get(0).substring("Player 1 starting position: ".length()));
        positionPlayer2 = Integer.parseInt(this.puzzle.get(1).substring("Player 2 starting position: ".length()));
    }

    @Override
    public long solvePart01() {
        int dice = 0;
        log.info("Player 1 position: {}", positionPlayer1);
        log.info("Player 2 position: {}", positionPlayer2);

        PlayerState playerState01 = new PlayerState(positionPlayer1, 0);
        PlayerState playerState02 = new PlayerState(positionPlayer2, 0);
        while (true) {
            dice = playerState01.rollDice(dice);
            log.info("Player 1 position: {} dice {} scorePlayer1 {}", positionPlayer1, dice, playerState01.score);
            if (playerState01.score >= 1000) {
                return (long) playerState02.score * dice;
            }

            dice = playerState02.rollDice(dice);
            log.info("Player 2 position: {} dice {} scorePlayer2 {}", positionPlayer2, dice, playerState02.score);
            if (playerState02.score >= 1000) {
                return (long) playerState01.score * dice;
            }
        }
    }

    @Override
    public long solvePart02() {
        return puzzle.size() + 1;
    }

    @AllArgsConstructor
    static class PlayerState {
        private int position;
        private int score;

        private int rollDice(int dice) {
            this.position = updatePosition(dice, this.position);
            this.score = this.score + moveToSpace(this.position, 10);
            return dice + 3;
        }

        int updatePosition(int dice, int position) {
            return position + moveToSpace(dice + 1 + dice + 2 + dice + 3, 100);
        }

        int moveToSpace(int position, int number) {
            int i = position % number;
            int move = i == 0 ? number : i;
            log.info("moveToSpace {} number {}", move, number);
            return move;
        }
    }
}

