package org.j55.exercise.deadlock;

import java.util.Map;

/**
 * Wydmuszka systemu rozliczeniowego.
 */
public abstract class TransferSystem {

    public static void transfer(BankAccount source, BankAccount target, int amount) {
      Map.Entry<BankAccount,  BankAccount> transactionAccounts =
          Map.entry(source, target);
        synchronized (transactionAccounts) {
                source.widthraw(amount);
                target.deposit(amount);
        }
    }
}
