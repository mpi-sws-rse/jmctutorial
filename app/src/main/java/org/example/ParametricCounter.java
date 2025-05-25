package org.example;

import org.mpisws.jmc.util.concurrent.JmcReentrantLock;

public class ParametricCounter {

    private final Counter counter;
    private final JmcReentrantLock lock;
    private final int numThreads;

    public ParametricCounter(int numThreads) {
        this.numThreads = numThreads;
        this.counter = new Counter();
        this.lock = new JmcReentrantLock();
    }

    public void run() {
        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(counter, lock);
            threads[i].start();
        }

        for (CounterThread thread : threads) {
            try {
                thread.join1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getCounterValue() {
        return counter.get();
    }
}
