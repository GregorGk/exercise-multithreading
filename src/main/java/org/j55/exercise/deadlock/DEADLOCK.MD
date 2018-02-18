# Deadlock
Zakleszczenie jest to jeden z najczęściej spotykanych problemów przy przetwarzaniu wielowątkowym. Jest to sytuacja kiedy przynajmniej dwa wątki czekają na wzajemnie zwolnienie przetrzymywanych zasobów, więc żaden z nich nie może zakończyć przetwarzania. Innymi słowy do zakleszczenia dojdzie wtedy gdy:

1. Z zasobu może korzystać tylko jeden wątek,
2. *Zasób A* jest zablokowany przez pierwszy wątek, ale do ukończenia zadania potrzebny jest *zasób B* przetrzymywany przez drugi wątek,
3. Wystąpił cykl zależności pomiędzy wątkami - jeśli "ostatni" w kolejce wątek oczekuje na zakońćzenie pierwszego, dojdzie do zakleszczenia.
4. Nie istnieje żaden mechanizm, który nie kończy wykonania wątku, w sytuacji, gdy zasób jest niedostępny - w praktyce taki mechanizm jest ciężki do implementacji i zależy od problemu, którego dotyczy przetwarzanie wielowątkowe.

## Problem

Wyobraźmy sobie, że mamy do czynienia z bankowym systemem rozliczeniowym . Metoda *TransferSystem.transfer* przedstawia mocno uproszczony schemat wykonania przelewu pomiędzy dwoma rachunkami:

```java
public static void transfer(BankAccount source, BankAccount target, int amount) {
    synchronized (source) {
        source.widthraw(amount);
        synchronized (target) {
            target.deposit(amount);
        }
    }
}
```

Przelew w systemie bankowym jest operacją atomową, w której muszą mieć miejsce następujące operacje: 

1. Zablokowanie rachunku źródłowego, aby inny wątek nie nie zmodyfikował jego bilansu
2. Pobranie środków z rachunku źródłowego
3. Zablokowanie rachunku docelowego, aby inny wątek nie nie zmodyfikował jego bilansu
4. Zaksięgowanie środków na rachunku docelowym i zwolnienie blokady na obu rachunkach.

Metody *BankAccount.widthraw* i *BankAccount.deposit* symulują opóźnienie związane z dostępem do systemu centralnego banku.

W klasie *DeadlockTest* przedstawiono powyższy scenariusz. Mamy dwie instancje klasy BankAccount *accountA* i *accountB*. Niemal równocześnie zostały zlecone wzajemne przelewy z *accountA* na *accountB* i z *accountB* na *accountA*.:

```java
Thread thread1 = new Thread(() -> TransferSystem.transfer(accountA, accountB, 200), "Transfer from A to B");
Thread thread2 = new Thread(() -> TransferSystem.transfer(accountB, accountA, 500),"Transfer from B to A");
thread1.start();
thread2.start();

```

Przy tak zaimplementowanej metodzie *TransferSystem.transfer* żaden z wątków nie zakończy się ponieważ: 

1. Wątek 1 ustawia synchronizację na *accountA* i pobiera z niego środki, wątek 2 równocześnie wykonuje analogiczną operację z *accountB*
2. Wątek 1 próbuje ustawić lock na accountB, ale się nie udaje i czeka na zwolnienie zasobu przez wątek 2
3. Wątek 2 próbuje ustawić lock na accountA, ale się nie udaje i czeka na zwolnienie zasobu przez wątek 1

Oba wątki będą w nieskończoność czekać na siebie.

## Zadanie

Java sama w sobie nie posiada mechanizmów rozwiązania zakleszczenia kiedy takie wystąpi. Aplikacje należy projektować w taki sposób, aby uniknąć zakładania więcej niż jednego locka lub napisać mechanizm synchronizacji locków.

Przepisz kod tak, aby *DeadlockTest* zakończył się sukcesem. 