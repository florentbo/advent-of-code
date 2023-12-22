package be.bonamis.advent.year2023;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.stream.*;

import be.bonamis.advent.year2023.Day20.DestinationModule.Pulse;
import be.bonamis.advent.year2023.Day20.DestinationModule.State;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.year2023.Day20.DestinationModule.Pulse.*;
import static be.bonamis.advent.year2023.Day20.DestinationModule.State.*;

@Slf4j
@Getter
public class Day20 extends TextDaySolver {

  private static final String BUTTON = "button";
  private static final String BROADCASTER = "broadcaster";
  private final Map<String, List<String>> moduleConfiguration;
  private final Map<String, State> states;
  private final Map<String, Map<String, Pulse>> pulses;

  long lowPulses = 0;
  long highPulses = 0;

  public Day20(List<String> puzzle) {
    super(puzzle);
    this.moduleConfiguration = readConfiguration();
    this.states = states();
    this.pulses = pulses();
  }

  public Day20(String sample) {
    super(sample);
    this.moduleConfiguration = readConfiguration();
    this.states = states();
    this.pulses = pulses();
    log.debug("configuration: {}", this.moduleConfiguration);
    log.debug("states: {}", this.states);
    log.debug("fliFlops: {}", fliFlops());
    log.debug("conjunctions: {}", conjunctions());
    log.debug("pulses: {}", pulses());
  }

  private Map<String, State> states() {
    return this.moduleConfiguration.keySet().stream()
        .collect(
            Collectors.toMap(
                key -> (key.contains("&") || key.contains("%")) ? key.substring(1) : key,
                key -> OFF));
  }

  private Map<String, Map<String, Pulse>> pulses() {
    return conjunctions().keySet().stream().collect(Collectors.toMap(key -> key, this::toMap));
  }

  private Map<String, Pulse> toMap(String key) {
    return fliFlops().entrySet().stream()
        .filter(entry -> entry.getValue().contains(key))
        .map(Map.Entry::getKey)
        .collect(Collectors.toMap(key2 -> key2, key2 -> LOW));
  }

  @Override
  public long solvePart01() {
    Counts counts = pushButton(1000L);
    return counts.low() * counts.high();
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  Counts pushButton(long times) {
    for (int i = 0; i < times; i++) {
      pushButton();
    }
    return new Counts(lowPulses, highPulses);
  }

  private void pushButton() {
    Queue<DestinationModule> queue = new LinkedList<>();
    log.debug("queue: {}", queue);
    sendPulses(BUTTON, LOW, queue, "start");
    while (!queue.isEmpty()) {
      DestinationModule module = queue.poll();
      log.debug("queue after poll size: {} content {}", queue.size(), queue);
      log.debug("queue poll : {}", module);
      sendPulses(module.destination(), module.pulse(), queue, module.origin());
    }
  }

  record Counts(Long low, Long high) {}

  private void sendPulses(
      String origin, Pulse pulse, Queue<DestinationModule> queue, String origin2) {
    if (origin.equals(BUTTON) || origin.equals(BROADCASTER)) {
      List<String> destinations = this.moduleConfiguration.get(origin);
      destinations.forEach(destination -> addToQueue(origin, queue, destination, LOW));
    } else {
      fliFlopDestinations(origin)
          .ifPresent(
              destinations -> {
                if (pulse == LOW) {
                  Pulse newPulse = handlePulse(origin);
                  destinations.forEach(
                      destination -> addToQueue(origin, queue, destination, newPulse));
                }
              });
      conjunctionDestinations(origin)
          .ifPresent(
              destinations2 -> {
                Pulse newPulse = handleConjPulse(origin, pulse, origin2);
                destinations2.forEach(
                    destination -> addToQueue(origin, queue, destination, newPulse));
              });
    }
  }

  private Pulse handlePulse(String origin) {
    State state = states.get(origin);
    State newState = state == OFF ? ON : OFF;
    Pulse newPulse = state == OFF ? HIGH : LOW;
    states.put(origin, newState);
    return newPulse;
  }

  private Pulse handleConjPulse(String destination, Pulse receivedPulse, String origin) {
    log.debug("handleConjPulse origin: {}", origin);
    log.debug("handleConjPulse destination: {}", destination);

    Map<String, Pulse> actualRemember = pulses.get(destination);
    log.debug("actualRemember: {}", actualRemember);

    actualRemember.put(origin, receivedPulse);
    log.debug("actualRemember with new reception: {}", actualRemember);

    /*
    Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
     */
    pulses.put(destination, actualRemember);
    boolean allHighs = actualRemember.values().stream().allMatch(pulse -> pulse.equals(HIGH));
    Pulse pulseToSend = allHighs ? LOW : HIGH;

    log.debug(
        "origin  {}  remember pulse {} received pulse {}",
        destination,
        actualRemember,
        receivedPulse);
    log.debug("pulse to send {} ", pulseToSend);

    return pulseToSend;
  }

  private void addToQueue(
      String origin, Queue<DestinationModule> queue, String destination, Pulse newPulse) {
    log.debug("----------------  {} -{}-> {}", origin, newPulse, destination);
    updateCounts(newPulse);
    queue.add(new DestinationModule(destination, newPulse, origin));
    log.debug("queue after add size: {} content {}", queue.size(), queue);
  }

  private void updateCounts(Pulse pulse) {
    if (pulse == HIGH) {
      highPulses++;
    } else {
      lowPulses++;
    }
  }

  private Optional<List<String>> fliFlopDestinations(String origin) {
    return fliFlops().containsKey(origin) ? Optional.of(fliFlops().get(origin)) : Optional.empty();
  }

  private Optional<List<String>> conjunctionDestinations(String origin) {
    return conjunctions().containsKey(origin)
        ? Optional.of(conjunctions().get(origin))
        : Optional.empty();
  }

  record DestinationModule(String destination, Pulse pulse, String origin) {
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
    String content = FileHelper.content("2023/20/2023_20_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day20 day = new Day20(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
