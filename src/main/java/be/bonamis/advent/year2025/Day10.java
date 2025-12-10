package be.bonamis.advent.year2025;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day10 extends TextDaySolver {

  private final Input input;

  public Day10(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day10(String input) {
    super(input);
    this.input = Input.of(this.puzzle);
  }

  @Override
  public long solvePart01() {
    log.info("Machines: {}", this.input.machines());
    return this.input.machines().stream().mapToLong(Input.Machine::solve).sum();
  }

  @Override
  public long solvePart02() {
    return 11;
  }

  record Input(List<Machine> machines) {
    public static Input of(List<String> puzzle) {
      List<Machine> machines = puzzle.stream().map(Machine::of).toList();
      return new Input(machines);
    }

    record Machine(IndicatorLightDiagram diagram, ButtonWiringScheme wiringScheme) {
      static Machine of(String input) {
        int diagramStart = input.indexOf('[');
        int diagramEnd = input.indexOf(']');
        String diagramStr = input.substring(diagramStart + 1, diagramEnd);
        IndicatorLightDiagram diagram = IndicatorLightDiagram.of(diagramStr);

        String wiringPart = input.substring(diagramEnd + 1);
        ButtonWiringScheme wiringScheme = ButtonWiringScheme.of(wiringPart);

        return new Machine(diagram, wiringScheme);
      }

      int solve() {
        IndicatorLightDiagram allOff = diagram.allOff();
        List<ButtonWiringScheme.ButtonWiring> buttons = wiringScheme.wirings();

        int n = buttons.size();

        for (int numPresses = 0; numPresses <= n; numPresses++) {
          if (tryAllCombinations(allOff, buttons, numPresses, 0, new ArrayList<>())) {
            return numPresses;
          }
        }

        throw new RuntimeException("No solution found");
      }

      private boolean tryAllCombinations(
          IndicatorLightDiagram current,
          List<ButtonWiringScheme.ButtonWiring> buttons,
          int numPresses,
          int startIdx,
          List<Integer> pressedButtons) {
        if (pressedButtons.size() == numPresses) {
          IndicatorLightDiagram result = current;
          for (int idx : pressedButtons) {
            result = result.toggle(buttons.get(idx));
          }
          return result.equals(diagram);
        }

        for (int i = startIdx; i < buttons.size(); i++) {
          pressedButtons.add(i);
          if (tryAllCombinations(current, buttons, numPresses, i + 1, pressedButtons)) {
            return true;
          }
          pressedButtons.remove(pressedButtons.size() - 1);
        }

        return false;
      }

      record IndicatorLightDiagram(List<Indicator> indicators) {

        static IndicatorLightDiagram of(String diagramStr) {
          log.debug("diagramStr: {}", diagramStr);
          List<Indicator> indicators =
              diagramStr
                  .chars()
                  .mapToObj(c -> String.valueOf((char) c))
                  .map(Indicator::fromSymbol)
                  .toList();
          return new IndicatorLightDiagram(indicators);
        }

        IndicatorLightDiagram allOff() {
          return new IndicatorLightDiagram(
              IntStream.range(0, indicators.size()).mapToObj(i -> Indicator.OFF).toList());
        }

        static IndicatorLightDiagram of(List<Indicator> indicators) {
          return new IndicatorLightDiagram(indicators);
        }

        IndicatorLightDiagram toggle(ButtonWiringScheme.ButtonWiring button) {
          var newIndicators = new ArrayList<>(this.indicators);
          for (Integer connection : button.connections()) {
            int index = connection;
            Indicator current = newIndicators.get(index);
            Indicator toggled = (current == Indicator.ON) ? Indicator.OFF : Indicator.ON;
            newIndicators.set(index, toggled);
          }
          return IndicatorLightDiagram.of(newIndicators);
        }

        public enum Indicator {
          ON("#"),
          OFF(".");

          public final String symbol;

          Indicator(String symbol) {
            this.symbol = symbol;
          }

          static Indicator fromSymbol(String symbol) {
            return Arrays.stream(Indicator.values())
                .filter(ind -> ind.symbol.equals(symbol))
                .findFirst()
                .orElseThrow();
          }
        }
      }

      record ButtonWiringScheme(List<ButtonWiring> wirings) {

        static ButtonWiringScheme of(String wiringPart) {
          List<ButtonWiring> wirings = new ArrayList<>();
          int pos = 0;
          while (pos < wiringPart.length()) {
            int start = wiringPart.indexOf('(', pos);
            if (start == -1) break;
            int end = wiringPart.indexOf(')', start);
            if (end == -1) break;

            String wiringContent = wiringPart.substring(start + 1, end);
            if (!wiringContent.isEmpty()) {
              List<Integer> connections =
                  Arrays.stream(wiringContent.split(","))
                      .map(String::trim)
                      .filter(s -> !s.isEmpty())
                      .map(Integer::parseInt)
                      .toList();
              wirings.add(new ButtonWiring(connections));
            } else {
              wirings.add(new ButtonWiring(List.of()));
            }
            pos = end + 1;
          }

          return new ButtonWiringScheme(wirings);
        }

        record ButtonWiring(List<Integer> connections) {}
      }
    }
  }
}
