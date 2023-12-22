package be.bonamis.advent.year2015;

import org.junit.jupiter.api.Test;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;
import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

  @Test
  void toDirection() {
    assertThat(new Day03("^>v<").toDirection('^')).isEqualTo(NORTH);
  }

  @Test
  void solvePart01() {
    assertThat(new Day03(">").solvePart01()).isEqualTo(2);
    assertThat(new Day03("^>v<").solvePart01()).isEqualTo(4);
    assertThat(new Day03("^v^v^v^v^v").solvePart01()).isEqualTo(2);
  }

  @Test
  void solvePart02() {
    assertThat(new Day03("^v").solvePart02()).isEqualTo(3);
    assertThat(new Day03("^>v<").solvePart02()).isEqualTo(3);
    assertThat(new Day03("^v^v^v^v^v").solvePart02()).isEqualTo(11);
  }
}
