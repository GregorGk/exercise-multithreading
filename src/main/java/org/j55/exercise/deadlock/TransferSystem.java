package org.j55.exercise.deadlock;

/**
 * Wydmuszka systemu rozliczeniowego.
 */
public abstract class TransferSystem {

    public static void transfer(BankAccount source, BankAccount target, int amount) {
        synchronized (source) {
            source.widthraw(amount);
            synchronized (target) {
                target.deposit(amount);
            }
        }
    }
}
