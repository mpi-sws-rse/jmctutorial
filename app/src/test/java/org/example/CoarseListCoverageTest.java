package org.example;

import org.example.list.DeletionThread;
import org.example.list.InsertionThread;
import org.example.list.Set;
import org.example.list.coarse.CoarseList;
import org.junit.jupiter.api.Test;
import org.mpisws.jmc.checker.JmcCheckerConfiguration;
import org.mpisws.jmc.checker.JmcFunctionalTestTarget;
import org.mpisws.jmc.checker.JmcModelChecker;
import org.mpisws.jmc.checker.exceptions.JmcCheckerException;
import org.mpisws.jmc.strategies.RandomSchedulingStrategy;
import org.mpisws.jmc.strategies.trust.MeasureGraphCoverageStrategy;
import org.mpisws.jmc.strategies.trust.MeasureGraphCoverageStrategyConfig;

public class CoarseListCoverageTest {

    // Same program as the CoarseListTest
    private void test_50_50_workload_coarse_list(int NUM_THREADS) {

        int NUM_INSERTIONS = (int) Math.ceil(NUM_THREADS / 2.0);
        int NUM_DELETIONS = (int) Math.floor(NUM_THREADS / 2.0);

        int[] arr = new int[NUM_INSERTIONS];
        for (int i = 0; i < NUM_INSERTIONS; i++) {
            arr[i] = i + 1; // Fixing input data
        }

        Set set = new CoarseList();

        InsertionThread[] insertionThreads = new InsertionThread[NUM_INSERTIONS];
        for (int i = 0; i < NUM_INSERTIONS; i++) {
            int item = arr[i];
            InsertionThread ithread = new InsertionThread(set, item);
            insertionThreads[i] = ithread;
        }

        DeletionThread[] deleteThreads = new DeletionThread[NUM_DELETIONS];
        for (int i = 0; i < NUM_DELETIONS; i++) {
            int item = arr[i];
            DeletionThread dthread = new DeletionThread(set, item);
            deleteThreads[i] = dthread;
        }

        for (int i = 0; i < NUM_INSERTIONS; i++) {
            insertionThreads[i].start();
        }

        for (int i = 0; i < NUM_DELETIONS; i++) {
            deleteThreads[i].start();
        }

        for (int i = 0; i < NUM_INSERTIONS; i++) {
            try {
                insertionThreads[i].join();
            } catch (InterruptedException e) {

            }
        }

        for (int i = 0; i < NUM_DELETIONS; i++) {
            try {
                deleteThreads[i].join();
            } catch (InterruptedException e) {

            }
        }
    }

    @Test
    public void testCoarseListCoverage() throws JmcCheckerException {
        JmcCheckerConfiguration config = new JmcCheckerConfiguration.Builder()
                .numIterations(100)
                .strategyConstructor((sConfig) -> {
                    return new MeasureGraphCoverageStrategy(
                            new RandomSchedulingStrategy(sConfig.getSeed()),
                            MeasureGraphCoverageStrategyConfig.builder()
                                    .recordPath(sConfig.getReportPath())
                                    .recordPerIteration()
                                    .build()
                    );
                })
                .build();

        JmcFunctionalTestTarget target = new JmcFunctionalTestTarget(
                "CoarseListCoverage",
                () -> {
                    test_50_50_workload_coarse_list(5);
                }
        );

        JmcModelChecker checker = new JmcModelChecker(config);
        checker.check(target);
    }
}
