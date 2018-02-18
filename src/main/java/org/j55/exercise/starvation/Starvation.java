package org.j55.exercise.starvation;

/**
 * @author johnnyFiftyFive
 */
public class Starvation {

    public static void main(String[] args) {
        Worker worker = new Worker();

        for (int i = 0; i < 10; i++) {
            new Thread(worker::doWork).start();
        }
    }

    static class Worker {
        synchronized void doWork() {
            String name = Thread.currentThread().getName();

            while (true) {
                System.out.println("Zasób zablokowany przez wątek: " + name);
            }
        }
    }
}
