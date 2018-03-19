package org.j55.exercise.forkjoin;

import java.util.concurrent.RecursiveTask;

public class MaxValueTask extends RecursiveTask<Integer> {
    private static final int MAX_WORK_SIZE = 10000;

    private int start;
    private int end;
    private int[] array;

    public MaxValueTask(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if (end - start <= MAX_WORK_SIZE) {
            Integer result = 0;
            for (int i = start; i < end; i++) {
                result = (array[i] > result)?
                    array[i] : result;
            }
            return result;
        } else {
            int division = (start + end) / 2;
            MaxValueTask left = new MaxValueTask(start, division, array);
            MaxValueTask rigth = new MaxValueTask(division, end, array);
            left.fork(); // pierwszą część zadania uruchamiamy w kolejnym wątku
            Integer rigthResult = rigth.compute(); // drugą część uruchamiamy w wątku bieżącym
            Integer leftResult = left.join(); // oczekujemy na zakończenie pierwszej części
            return (leftResult > rigthResult)?
                leftResult : rigthResult;
        }
    }
}
