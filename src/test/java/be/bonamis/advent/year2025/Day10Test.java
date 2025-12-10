package be.bonamis.advent.year2025;

import static be.bonamis.advent.year2025.Day10.Input.Machine.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2025.Day10.Input.Machine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day10Test {
  private static final String INPUT =
      """
			[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
			[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
			[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
			""";

  @Test
  void solvePart01() {
    Day10 day10 = new Day10(INPUT);
	  List<Machine> machines = day10.getInput().machines();
	  Machine machine = machines.get(0);
	  IndicatorLightDiagram diagram = machine.diagram();
	  log.debug("Diagram: {}", diagram);

	  ButtonWiringScheme wiringScheme = machine.wiringScheme();
	  List<ButtonWiringScheme.ButtonWiring> wirings = wiringScheme.wirings();
	  var lastWiring = wirings.get(wirings.size() - 1);
	  var penultimateWiring = wirings.get(wirings.size() - 2);

	  log.debug("Last wiring: {}", lastWiring);
	  log.debug("Penultimate wiring: {}", penultimateWiring);

	  IndicatorLightDiagram allOff = diagram.allOff();
	  IndicatorLightDiagram toggleOnce = allOff.toggle(penultimateWiring);
	  log.debug("Toggle once: {}", toggleOnce);
	  IndicatorLightDiagram toggleTwice = toggleOnce.toggle(lastWiring);
	  log.debug("Toggle twice: {}", toggleTwice);
	  assertThat(toggleTwice).isEqualTo(diagram);

	  assertThat(machine.solve()).isEqualTo(2);
	  assertThat(machines.get(1).solve()).isEqualTo(3);
	  assertThat(machines.get(2).solve()).isEqualTo(2);

	  assertThat(day10.solvePart01()).isEqualTo(7);
  }
}
