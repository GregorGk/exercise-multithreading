package org.j55.exercise.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author johnnyFiftyFive
 */
public class SumTask extends RecursiveTask<Long> {
    private static final int MAX_WORK_SIZE = 10000;

    private int start;
    private int end;
    private int[] array;

    public SumTask(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    protected Long compute() {
        if (end - start <= MAX_WORK_SIZE) {
            long result = 0;
            for (int i = start; i < end; i++) {
                result += array[i];
            }
            return result;
        } else {
            int division = (start + end) / 2;
            SumTask left = new SumTask(start, division, array);
            SumTask rigth = new SumTask(division, end, array);
            left.fork(); // pierwszą część zadania uruchamiamy w kolejnym wątku
            long rigthResult = rigth.compute(); // drugą część uruchamiamy w wątku bieżącym
            long leftResult = left.join(); // oczekujemy na zakończenie pierwszej części
            return rigthResult + leftResult;
        }
    }
}

