# Starvation

Zagłodzenie ma miejsce w sytuacji, kiedy jeden zachłanny wątek przetrzymuje zasób, przez co pozostałe wątki nigdy nie dostają szansy skorzystania z niego. Wątki zostają zagłodzone przy następujących scenariuszach:

1. Wątek blokujący wykonuje blokujący kod w synchronizowanym bloku w nieskończoność - np. operacje I/O, nieskończone pętle, etc.,
2. Wątki o niższym priorytecie nigdy nie dostają dostępu do zasobu, ponieważ pierwszeństwo w dostepie do żądanego zasobu mają wątki o wyższym priorytecie,

## Problem

Klasa *Worker* posiada synchronizowaną metodę *doWork()*, która w pętli wywołuje zasób, w tym przypadku jest to wypisanie tekstu do konsoli (w rzeczywistym przypadku może być to strumień, baza danych, wiersz w tabeli, etc.). Przy synchronizacji na poziomie metody, w sytuacji kiedy z instancji *Worker* będzie chciało skorzystać kilka wątków, tylko jeden będzie mógł niej skorzystać, pozostałe w nieskończoność będą oczekiwać na zwolnienie zasobu:

```java
Zasób zablokowany przez wątek: Thread-0
Zasób zablokowany przez wątek: Thread-0
Zasób zablokowany przez wątek: Thread-0
Zasób zablokowany przez wątek: Thread-0
Zasób zablokowany przez wątek: Thread-0
Zasób zablokowany przez wątek: Thread-0
```

## Zadanie

Zmień implementacje metody *Worker.doWork* w taki sposób, aby pozostałe wątki mogły skorzystać z blokowanego zasobu.