package org.example;

import org.mpisws.jmc.util.concurrent.JmcReentrantLock;
import org.mpisws.jmc.util.concurrent.JmcThread;

/**
 * A thread that increments a shared counter using a reentrant lock.
 * This class is used to demonstrate thread-safe incrementing of a counter.
 */
public class CounterThread extends JmcThread {
    private final JmcReentrantLock lock;
    private final Counter counter;

    public CounterThread(Counter counter, JmcReentrantLock lock) {
        super();
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run1() {
        lock.lock();
        counter.set(counter.get() + 1);
        lock.unlock();
    }
}
