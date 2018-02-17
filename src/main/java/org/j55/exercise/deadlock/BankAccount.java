package org.j55.exercise.deadlock;

/**
 * Klasa reprezentująca rachunek bankowy.
 */
public class BankAccount {
    private int id;
    private int balance;

    public BankAccount(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    /**
     * Metoda dodaje określoną kwotę do bilansu konta
     *
     * @param amount kwota
     */
    public void deposit(int amount) {
        // symulacja dostępu do systemu centralnego
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            // ignorujemy, chociaż jest to nieładne
        }

        balance += amount;
    }

    /**
     * Metoda odejmuje określoną kwotę do bilansu konta
     *
     * @param amount kwota
     */
    public void widthraw(int amount) {
        // symulacja dostępu do systemu centralnego
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            // ignorujemy, chociaż jest to nieładne
        }

        balance -= amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
