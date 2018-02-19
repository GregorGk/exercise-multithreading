package org.j55.exercise.forkjoin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * @author johnnyFiftyFive
 */
@RunWith(JUnit4.class)
public class ForkJoinTest {

    private static final ForkJoinPool pool = new ForkJoinPool(10);

    @Test
    public void sumTest() {
        int elementsNumber = 100_000;
        int[] integers = IntStream.rangeClosed(1, elementsNumber).toArray();

        Long result = pool.invoke(new SumTask(0, elementsNumber, integers));
        Assert.assertEquals(5000050000L, result.longValue());
    }


    /**
     * Zaimplementuj klasę MaxValueTask, w taki sposób, aby znajdywała element o największej wartości w podanej w teście tablicy.
     */
    @Test
    public void maxElement() throws Exception {
        URL file = ForkJoinTest.class.getClassLoader().getResource("sequence.txt");
        byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
        String[] numbers = new String(bytes, "UTF-8").split("\n");

        int result = 0; // do uzupełnienia pool.invoke(...)
        Assert.assertEquals(10000, result);
    }


    /**
     * Znajdź nadłuższy ciąg wystąpienia liczby 66. Zaimplementuj we właściwy sposób klasę LongestSequenceTask.
     * Zadanie będzie wymagało implementacji pomocniczej klasy LongestSequenceResult, która będzie przechowywała
     * częściowy wynik z każdego wątku. Należy przemyśleć w jaki sposób łączyć wyniki z rozdzielonych zadań.
     */
    @Test
    public void theLongestSequenceTest() throws Exception {
        URL file = ForkJoinTest.class.getClassLoader().getResource("sequence.txt");
        byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
        String[] numbers = new String(bytes, "UTF-8").split("\n");

        int result = 0; // do uzupełnienia pool.invoke(...)
        Assert.assertEquals(10, result);
    }
}