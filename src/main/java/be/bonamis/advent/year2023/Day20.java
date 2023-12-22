package be.bonamis.advent.year2023;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.stream.*;

import be.bonamis.advent.year2023.Day20.DestinationModule.Pulse;
import be.bonamis.advent.year2023.Day20.DestinationModule.State;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import static be.bonamis.advent.year2023.Day20.DestinationModule.Pulse.*;
import static be.bonamis.advent.year2023.Day20.DestinationModule.State.*;

@Slf4j
@Getter
public class Day20 extends TextDaySolver {

  private static final String BUTTON = "button";
  private static final String BROADCASTER = "broadcaster";
  private final Map<String, List<String>> moduleConfiguration;
  private final Map<String, State> states;

  public Day20(List<String> puzzle) {
    super(puzzle);
    this.moduleConfiguration = readConfiguration();
    this.states = states();
  }

  public Day20(String sample) {
    super(sample);
    this.moduleConfiguration = readConfiguration();
    this.states = states();
    log.debug("configuration: {}", this.moduleConfiguration);
    log.debug("states: {}", this.states);
    log.debug("fliFlops: {}", fliFlops());
    log.debug("conjunctions: {}", conjunctions());
  }

  private Map<String, State> states() {
    return this.moduleConfiguration.keySet().stream()
        .collect(
            Collectors.toMap(
                key -> (key.contains("&") || key.contains("%")) ? key.substring(1) : key,
                key -> OFF));
  }

  @Override
  public long solvePart01() {
    return 101L;
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  void pushButton() {
    Pair<Long, Long> counts = Pair.of(0L, 0L);

    Queue<DestinationModule> queue = new LinkedList<>();
    log.debug("queue: {}", queue);
    sendPulses(BUTTON, LOW, queue);
    while (!queue.isEmpty()) {
      DestinationModule module = queue.poll();
      log.debug("queue after poll size: {} content {}", queue.size(), queue);
      String name = module.name();
      log.debug("queue poll name: {}", name);
      sendPulses(name, LOW, queue);
    }
  }

  private void sendPulses(String origin, Pulse pulse, Queue<DestinationModule> queue) {
    if (origin.equals(BUTTON) || origin.equals(BROADCASTER)) {
      List<String> destinations = this.moduleConfiguration.get(origin);
      destinations.forEach(destination -> addToQueue(origin, queue, destination, LOW));
    } else {
      fliFlopDestinations(origin)
          .ifPresent(
              destinations ->
                  destinations.forEach(
                      destination -> {
                        if (pulse == LOW) {
                          /*
                          If a flip-flop module receives a high pulse, it is ignored and nothing happens.
                          However, if a flip-flop module receives a low pulse, it flips between on and off.
                          If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
                           */
                          State state = states.get(origin);
                          // flip
                          State newState = state == OFF ? ON : OFF;
                          states.put(origin, newState);
                          Pulse newPulse = state == OFF ? HIGH : LOW;
                          addToQueue(origin, queue, destination, newPulse);
                        }
                      }));
      conjunctionDestinations(origin)
          .ifPresent(
              destinations2 ->
                  destinations2.forEach(
                      destination -> {
                        /*
                        Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
                        they initially default to remembering a low pulse for each input.
                        When a pulse is received, the conjunction module first updates its memory for that input.
                        Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
                        inv -low-> a
                         */
                        State state = states.get(origin);
                        Pulse newPulse = state == OFF ? LOW : HIGH;
                        addToQueue(origin, queue, destination, newPulse);
                      }));
    }
  }

  private void addToQueue(
      String origin, Queue<DestinationModule> queue, String destination, Pulse newPulse) {
    log.debug("{} -{}-> {}", origin, newPulse, destination);
    queue.add(new DestinationModule(destination, newPulse));
    log.debug("queue after add size: {} content {}", queue.size(), queue);
  }

  private Optional<List<String>> fliFlopDestinations(String origin) {
    return fliFlops().containsKey(origin) ? Optional.of(fliFlops().get(origin)) : Optional.empty();
  }

  private Optional<List<String>> conjunctionDestinations(String origin) {
    return conjunctions().containsKey(origin)
        ? Optional.of(conjunctions().get(origin))
        : Optional.empty();
  }

  record DestinationModule(String name, Pulse pulse) {
    enum State {
      ON,
      OFF
    }

    enum Pulse {
      LOW,
      HIGH
    }
  }

  private Map<String, List<String>> readConfiguration() {
    List<String> input = new ArrayList<>(this.puzzle);
    input.add("button -> " + BROADCASTER);
    return input.stream()
        .map(pair -> pair.split("->"))
        .collect(Collectors.toMap(pair -> pair[0].strip(), pair -> toList(pair[1].strip())));
  }

  private List<String> toList(String destinations) {
    return Arrays.stream(destinations.split(",")).map(String::strip).toList();
  }

  Map<String, List<String>> fliFlops() {
    /* map.put(BUTTON, this.moduleConfiguration.get(BUTTON));
    map.put("broadcaster", this.moduleConfiguration.get("broadcaster"));*/
    // Map<String, List<String>> map = new HashMap<>(filterModules("%"));
    return filterModules("%");
  }

  Map<String, List<String>> conjunctions() {
    return filterModules("&");
  }

  private Map<String, List<String>> filterModules(String prefix) {
    return this.moduleConfiguration.entrySet().stream()
        .filter(entry -> entry.getKey().contains(prefix))
        .collect(Collectors.toMap(entry -> entry.getKey().substring(1), Map.Entry::getValue));
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/19/2023_19_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day20 day = new Day20(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
