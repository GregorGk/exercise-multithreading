package org.j55.exercise.forkjoin;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author johnnyFiftyFive
 */
public class LongestSequenceTask extends RecursiveTask<LongestSequenceResult> {
    private static final int MAX_WORK_SIZE = 3;

    private int start;
    private int end;
    private int[] array;

    public LongestSequenceTask(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    protected LongestSequenceResult compute() {
        if (end - start <= MAX_WORK_SIZE) {
            return solveSubproblem(0, array.length);
        } else {
            int division = (start + end) / 2;
            LongestSequenceTask left = new LongestSequenceTask(start, division, array);
            LongestSequenceTask rigth = new LongestSequenceTask(division, end, array);
            left.fork(); // pierwszą część zadania uruchamiamy w kolejnym wątku
            LongestSequenceResult rigthResult = rigth.compute(); // drugą część uruchamiamy w wątku bieżącym
            LongestSequenceResult leftResult = left.join(); // oczekujemy na zakończenie pierwszej części
            return this.mergeResults(leftResult, rigthResult);
        }
    }

    private LongestSequenceResult solveSubproblem(int fromIndex, int toIndex) {
        boolean previousBelongsToDesiredSequence;
        boolean currentBelongsToDesiredSequence = false;
        boolean containsOnlyDesiredNumbers = true;
        int currentSequenceLength = 0;

        List<Integer> sequenceLengths = new LinkedList();

        for(int i = fromIndex; i < toIndex; i++) {
            previousBelongsToDesiredSequence = currentBelongsToDesiredSequence;
            currentBelongsToDesiredSequence = array[i] == 66;
            if (!currentBelongsToDesiredSequence) {
                containsOnlyDesiredNumbers = false;
            }
            if (previousBelongsToDesiredSequence && currentBelongsToDesiredSequence) {
                currentSequenceLength++;
            } else if (!(previousBelongsToDesiredSequence) && currentBelongsToDesiredSequence) {
                currentSequenceLength = 1;
            } else {
                if (currentSequenceLength != 0)
                    sequenceLengths.add(currentSequenceLength);
                currentSequenceLength = 0;
            }
        }
        boolean isFirstElementTheDesiredOne = array[fromIndex] == 66;
        boolean isLastElementTheDesiredOne = array[toIndex - 1] == 66;
        int longestSequenceLength = sequenceLengths.stream()
            .max(Integer::compareTo).orElse(0);

        boolean beginsWithLongestSequence = isFirstElementTheDesiredOne
            && sequenceLengths.get(0) == longestSequenceLength;
        boolean endsWithLongestSequence = isLastElementTheDesiredOne
            && sequenceLengths.get(sequenceLengths.size() - 1) == longestSequenceLength;

        return
            new LongestSequenceResult(
                beginsWithLongestSequence,
                endsWithLongestSequence,
                containsOnlyDesiredNumbers,
                longestSequenceLength);
    }

    private LongestSequenceResult mergeResults(
        LongestSequenceResult left,
        LongestSequenceResult right
        ) {
        LongestSequenceResult result;
        if (left.endsWithLongestSequence && right.beginsWithLongestSequence)
            result = new LongestSequenceResult(
                left.beginsWithLongestSequence,
                right.endsWithLongestSequence,
                left.containsOnlyDesiredNumbers && right.containsOnlyDesiredNumbers,
                left.getLength() + right.getLength());
        else
            result = LongestSequenceResult.getSimplyLonger(left, right);
        return result;
    }
}
