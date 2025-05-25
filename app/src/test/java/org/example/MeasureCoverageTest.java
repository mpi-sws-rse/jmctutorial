package org.example;

import org.junit.jupiter.api.Test;
import org.mpisws.jmc.checker.JmcCheckerConfiguration;
import org.mpisws.jmc.checker.JmcFunctionalTestTarget;
import org.mpisws.jmc.checker.JmcModelChecker;
import org.mpisws.jmc.checker.exceptions.JmcCheckerException;
import org.mpisws.jmc.strategies.RandomSchedulingStrategy;
import org.mpisws.jmc.strategies.trust.MeasureGraphCoverageStrategy;
import org.mpisws.jmc.strategies.trust.MeasureGraphCoverageStrategyConfig;

public class MeasureCoverageTest {

    /**
     * This test checks measures the graph coverage of the random scheduling strategy
     * by running a parametric counter test with 1000 iterations.
     *
     * The graphs coverage are recorded in the default report path - build/test-results/jmc-report
     */
    @Test
    public void testRandomMeasuringCoverage() throws JmcCheckerException {
        JmcCheckerConfiguration config = new JmcCheckerConfiguration.Builder()
                .numIterations(100)
                .strategyConstructor((sconfig) -> {
                    return new MeasureGraphCoverageStrategy(
                            new RandomSchedulingStrategy(sconfig.getSeed()),
                            new MeasureGraphCoverageStrategyConfig.MeasureGraphCoverageStrategyConfigBuilder()
                                    .recordPath(sconfig.getReportPath())
                                    .recordPerIteration()
                                    .build()
                    );
                })
                .build();

        JmcFunctionalTestTarget target = new JmcFunctionalTestTarget(
                "ParametricCounterTest",
                () -> {
                    ParametricCounter parametricCounter = new ParametricCounter(5);
                    parametricCounter.run();

                    // The counter should be equal to the number of threads.
                    assert parametricCounter.getCounterValue() == 5 :
                        "Counter value should be 5, but is " + parametricCounter.getCounterValue();
                }
        );

        JmcModelChecker checker = new JmcModelChecker(config);
        checker.check(target);
    }
}
