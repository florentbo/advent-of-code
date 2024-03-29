package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import java.util.List;

import be.bonamis.advent.year2023.Day19.Rating;
import be.bonamis.advent.year2023.Day19.WorkFlow;
import be.bonamis.advent.year2023.Day19.WorkFlow.Rule;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
class Day19Test {

  private Day19 day19;

  @BeforeEach
  void setUp() {
    String sample =
        """
        px{a<2006:qkq,m>2090:A,rfg}
        pv{a>1716:R,A}
        lnx{m>1548:A,A}
        rfg{s<537:gd,x>2440:R,A}
        qs{s>3448:A,lnx}
        qkq{x<1416:A,crn}
        crn{x>2662:A,R}
        in{s<1351:px,qqz}
        qqz{s>2770:qs,m<1801:hdj,R}
        gd{a>3333:R,R}
        hdj{m>838:A,pv}

        {x=787,m=2655,a=1222,s=2876}
        {x=1679,m=44,a=2067,s=496}
        {x=2036,m=264,a=79,s=2244}
        {x=2461,m=1339,a=466,s=291}
        {x=2127,m=1623,a=2188,s=1013}
                      """;
    day19 = new Day19(Arrays.asList(sample.split("\n")));
  }

  @Test
  void parseRating() {
    String input = "{x=787,m=2655,a=1222,s=2876}";
    Rating rating = Rating.parse(input);
    assertThat(rating.values()).isEqualTo(Map.of("x", 787L, "m", 2655L, "a", 1222L, "s", 2876L));
  }

  @Test
  void parseInput() {
    assertThat(day19.getWorkFlows().workFlows()).hasSize(11);
    assertThat(day19.getRatings().ratings()).hasSize(5);
  }

  @Test
  void parseWorkflow() {
    String input = "qqz{s>2770:qs,m<1801:hdj,R}";
    assertThat(WorkFlow.parse(input))
        .isEqualTo(
            new WorkFlow(
                "qqz", List.of(new Rule("s>2770:qs"), new Rule("m<1801:hdj"), new Rule("R"))));
  }

  @Test
  void start() {
    assertThat(day19.getWorkFlows().start().name()).isEqualTo("in");
  }

  @ParameterizedTest
  @CsvSource({"0,true", "1,false", "2,true", "3,false", "4,true"})
  void isAccepted(int input, boolean expected) {
    Day19.WorkFlows workFlows = day19.getWorkFlows();
    assertThat(day19.getRatings().ratings().get(input).isAccepted(workFlows)).isEqualTo(expected);
  }

  @Test
  void rating_condition() {
    String input = "{x=787,m=2655,a=1222,s=2876}";
    Rating rating = Rating.parse(input);
    Rule.Condition condition = Rule.Condition.from("value=s<1351");
    assertThat(condition.test(rating)).isFalse();
  }

  @Test
  void rating_total() {
    String input = "{x=787,m=2655,a=1222,s=2876}";
    Rating rating = Rating.parse(input);
    assertThat(rating.total()).isEqualTo(7540L);
  }

  @Test
  void solvePart01() {
    assertThat(day19.solvePart01()).isEqualTo(19114);
  }
}
