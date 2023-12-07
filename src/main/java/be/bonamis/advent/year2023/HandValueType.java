package be.bonamis.advent.year2023;

import lombok.Getter;

@Getter
public enum HandValueType {
  FIVE_OF_A_KIND("Four of a Kind", 8),
  FOUR_OF_A_KIND("Four of a Kind", 7),
  FULL_HOUSE("a Full House", 6),
  THREE_OF_A_KIND("Three of a Kind", 3),
  TWO_PAIRS("Two Pairs", 2),
  ONE_PAIR("One Pair", 1),
  HIGH_CARD("a High Card", 0),
  ;

  /**
   * The description. -- GETTER -- Returns the description.
   *
   * @return The description.
   */
  private String description;

  /**
   * The hand value. -- GETTER -- Returns the hand value.
   *
   * @return The hand value.
   */
  private int value;

  /**
   * Constructor.
   *
   * @param description The description.
   * @param value The hand value.
   */
  HandValueType(String description, int value) {
    this.description = description;
    this.value = value;
  }
}
