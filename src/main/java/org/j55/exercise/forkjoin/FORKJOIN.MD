# Fork/Join framework

Fork/join framework to grupa klas, które zostały zaimplementowane w celu łatwiejszego wykorzystania potencjału wielordzeniowych procesorów. Schemat jego wykorzystania opiera się na rekurencji - arbitralnie duże zadanie rozbijane jest na dwa mniejsze wykonywane w dwóch różnych wątkach, jeśli nowe zadania dalej są za duże są ponownie są rozbijane na mniejsze, dopóki nasza "paczka" nie osiągnie rozmiaru, który uznamy, że jest wykonalny na jednym wątku.

*ForkJoinPool*, w którym wykonywane są poszczególne zadania implementuje dobrze znany interfejs *ExecutorService*, który jest używany do dystrybuowania zadań pomiędzy wątki. W standardowych implementacjach *ExecutorService*, z góry było wiadomo, który wątek będzie wykonywał dane zadanie. Nawet w sytuacji, jeśli kolejka zadań na wątku A była pusta, a na kolejkach innych wątków dalej oczekiwały zadania do uruchomienia, to nie następowało przeniesienie zadań na wątek A, który był bezczynny.

Implementacja *ForkJoinPool* rozrzuca oczekujące zadania równo po każdym wątku, a jeśli jeden z wątków wykona swoją pracę szybciej niż inne, przerzuca zadania z bardziej obciążonych wątków na ten wolny.

## Przykład

```java
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
        if (end - start <= MAX_WORK_SIZE) { // 1 - sprawdzenie
            long result = 0;
            for (int i = start; i < end; i++) { // 2- wykonanie obliczeń
                result += array[i];
            }
            return result;
        } else {
            int division = (start + end) / 2; // 3
            SumTask left = new SumTask(start, division, array); // 4
            SumTask rigth = new SumTask(division, end, array); // 4
            left.fork(); // pierwszą część zadania uruchamiamy w kolejnym wątku
            long rigthResult = rigth.compute(); // drugą część uruchamiamy w wątku bieżącym
            long leftResult = left.join(); // oczekujemy na zakończenie pierwszej części
            return rigthResult + leftResult; // 5
        }
    }
}
```
Aby wykorzystać potencjał *ForkJoinPool* należy rozszerzyć klasę *RecursiveTask* i zaimplementować metodę *compute*. Jej działanie w każdym wypadku opiera się na jednym schemacie:

1. Sprawdzamy czy rozmiar zadania jest dla nas odpowiednio mały, żeby pojedyńczy wątek się nim zajął
2. Jeśli tak, wykonujemy obliczenia i zwracamy wynik
3. Jeśli nie jest, wyznaczamy warunek podziału zadania
4. Tworzymy nowe zadania, a następnie je uruchamiamy na osobnych wątkach
5. Dokonujemy redukcji wyniku

Uruchomienie zadania polega na wywołaniu metody *ForkJoinPool*.*invoke* przekazując jako argument implementację *RecursiveTask*:

```java
new ForkJoinPool(10).invoke(new SumTask(0, arrayOfInts.length, arrayOfInts))
```
Pełny przykład do analizy w klasach *ForkJoinTest* oraz *SumTask*.

## Zadania

Testy do wszystkich zadań znajdują się w *ForkJoinTest*. 

1. Zaimplementuj klasę *MaxValueTask*, w taki sposób, aby znajdywała element o największej wartości w podanej w teście tablicy.
2. Znajdź nadłuższy ciąg wystąpienia liczby 66. Zaimplementuj we właściwy sposób klasę *LongestSequenceTask*. Zadanie będzie wymagało implementacji pomocniczej klasy *LongestSequenceResult*, która będzie przechowywała częściowy wynik z każdego wątku. Należy przemyśleć w jaki sposób łączyć wyniki z rozdzielonych zadań.