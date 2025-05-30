package org.example;

// Test that multiple threads can increment a counter without issues.

import org.mpisws.jmc.annotations.JmcCheck;
import org.mpisws.jmc.annotations.JmcCheckConfiguration;

import java.util.concurrent.locks.ReentrantLock;

public class CounterTest {

    // A program where two threads increment a shared counter.
    private void testTwoCounterIncrement() {
        Counter counter = new Counter();
        ReentrantLock lock = new ReentrantLock();

        CounterThread thread1 = new CounterThread(counter, lock);
        CounterThread thread2 = new CounterThread(counter, lock);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // The counter should be 2 after both threads increment it.
        assert counter.get() == 2 : "Counter value should be 2, but is " + counter.get();
    }

    // Running with JMC using the default configuration.
    @JmcCheck
    @JmcCheckConfiguration(numIterations = 100)
    public void runRandomCounterTest() {
        testTwoCounterIncrement();
    }

    @JmcCheck
    @JmcCheckConfiguration(strategy = "trust", numIterations = 100, debug=true)
    public void runTrustCounterTest() {
        testTwoCounterIncrement();
    }
}
