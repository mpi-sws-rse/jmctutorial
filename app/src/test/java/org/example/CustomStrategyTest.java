package org.example;

import org.example.strategies.WeightedRandomStrategy;
import org.junit.jupiter.api.Test;
import org.mpisws.jmc.checker.JmcCheckerConfiguration;
import org.mpisws.jmc.checker.JmcFunctionalTestTarget;
import org.mpisws.jmc.checker.JmcModelChecker;
import org.mpisws.jmc.checker.exceptions.JmcCheckerException;

import java.util.concurrent.locks.ReentrantLock;

public class CustomStrategyTest {

    // Same program as in CounterTest, two threads increment a shared counter.
    private void testProgram() {
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

    @Test
    void testUsingCustomStrategy() throws JmcCheckerException {
        // This test would use a custom strategy defined in the application.

        JmcCheckerConfiguration config = new JmcCheckerConfiguration.Builder()
                .numIterations(100)
                .strategyConstructor((sConfig) -> {
                    return new WeightedRandomStrategy(100);
                }).build();

        JmcFunctionalTestTarget target = new JmcFunctionalTestTarget(
                "ConcurrentCounterCustom",
                this::testProgram
        );

        JmcModelChecker checker = new JmcModelChecker(config);
        checker.check(target);
    }
}
