package org.example;

import org.mpisws.jmc.runtime.RuntimeUtils;

// A simple counter class that maintains an integer count.
// It provides methods to get and set the count, with JMC event logging for tracking changes.
public class Counter {
    private int count = 0;

    public Counter() {
        this.count = 0;
        RuntimeUtils.writeEvent(
                this, 0, "org/mpisws/jmc/programs/correct/counter/Counter", "count", "I");
    }

    public int get() {
        int out = count;
        RuntimeUtils.readEvent(
                this, "org/mpisws/jmc/programs/correct/counter/Counter", "count", "I");
        return out;
    }

    public void set(int value) {
        count = value;
        RuntimeUtils.writeEvent(
                this, value, "org/mpisws/jmc/programs/correct/counter/Counter", "count", "I");
    }
}