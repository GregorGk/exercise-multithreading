package org.j55.exercise.deadlock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DeadlockTest {

    @Test
    public void transferTest() {
        BankAccount accountA = new BankAccount(1, 1000);
        BankAccount accountB = new BankAccount(2, 1000);

        Thread thread1 = new Thread(() -> TransferSystem.transfer(accountA, accountB, 200), "Transfer from A to B");
        Thread thread2 = new Thread(() -> TransferSystem.transfer(accountB, accountA, 500),"Transfer from B to A");
        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()){
            System.out.println("[" + thread1.getName() + "] is " + thread1.getState());
            System.out.println("[" + thread2.getName() + "] is " + thread2.getState());
        }

        Assert.assertEquals(1300, accountA.getBalance());
        Assert.assertEquals(700, accountB.getBalance());
    }
}