package org.example;

import org.mpisws.jmc.annotations.JmcCheck;
import org.mpisws.jmc.annotations.JmcCheckConfiguration;

public class ParametricCounterTest {

    @JmcCheck
    @JmcCheckConfiguration(numIterations = 100)
    public void runRandomParametricCounterTest() {
        ParametricCounter parametricCounter = new ParametricCounter(5);
        parametricCounter.run();

        // The counter should be equal to the number of threads.
        assert parametricCounter.getCounterValue() == 5 :
            "Counter value should be 10, but is " + parametricCounter.getCounterValue();
    }

    @JmcCheck
    @JmcCheckConfiguration(strategy = "trust", numIterations = 100)
    public void runTrustParametricCounterTest() {
        ParametricCounter parametricCounter = new ParametricCounter(4);
        parametricCounter.run();

        // The counter should be equal to the number of threads.
        assert parametricCounter.getCounterValue() == 4 :
            "Counter value should be 10, but is " + parametricCounter.getCounterValue();
    }
}
