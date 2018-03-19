package org.j55.exercise.forkjoin;

/**
 * @author johnnyFiftyFive
 */
public class LongestSequenceResult {
    boolean beginsWithLongestSequence;
    boolean endsWithLongestSequence;
    boolean containsOnlyDesiredNumbers;

    int length;

  public LongestSequenceResult(
      boolean beginsWithLongestSequence,
      boolean endsWithLongestSequence,
      boolean containsOnlyDesiredNumber,
      int length) {
    this.beginsWithLongestSequence = beginsWithLongestSequence;
    this.endsWithLongestSequence = endsWithLongestSequence;
    this.containsOnlyDesiredNumbers = containsOnlyDesiredNumber;
    this.length = length;
  }

  /**
   * Does this sequence begin with the desired longest sequence?
   */
  public boolean beginsWithLongestSequence() {
    return beginsWithLongestSequence;
  }

  /**
   * Does this sequence end with the desired longest sequence?
   */
  public boolean endsWithLongestSequence() {
    return endsWithLongestSequence;
  }

  /**
   * E.g.
   * @return
   * {@true} for: 66, 66, 66.
   * {@false} for: 66, 11, 66.
   *
   */
  public boolean containsOnlyDesiredNumber() {
    return containsOnlyDesiredNumbers;
  }

  public int getLength() {
    return length;
  }

  public static LongestSequenceResult getSimplyLonger(
      LongestSequenceResult first,
      LongestSequenceResult second) {
    return (first.getLength() > second.getLength())?
        first : second;
  }
}
