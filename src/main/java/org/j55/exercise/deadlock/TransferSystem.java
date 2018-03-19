package org.j55.exercise.deadlock;

import java.util.Map;

/**
 * Wydmuszka systemu rozliczeniowego.
 */
public abstract class TransferSystem {

    public static void transfer(BankAccount source, BankAccount target, int amount) {
        synchronized (Map.entry(source, target)) {
                source.widthraw(amount);
                target.deposit(amount);
        }
    }
}
