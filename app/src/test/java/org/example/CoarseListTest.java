package org.example;

import org.example.list.DeletionThread;
import org.example.list.InsertionThread;
import org.example.list.Set;
import org.example.list.coarse.CoarseList;
import org.mpisws.jmc.annotations.JmcCheck;
import org.mpisws.jmc.annotations.JmcCheckConfiguration;

import java.util.ArrayList;
import java.util.List;

// Testing the CoarseList implementation with JMC checks
public class CoarseListTest {

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

        // Check if the set contains the expected elements (trivially the first element)
        assert !set.contains(0);
    }

    // Running with JMC using the default configuration. (random
    @JmcCheck
    @JmcCheckConfiguration(numIterations = 100)
    public void runRandomCoarseListTest() {
        test_50_50_workload_coarse_list(6);
    }

    // Running with JMC using the trust strategy.
    @JmcCheck
    @JmcCheckConfiguration(strategy = "trust", numIterations = 130)
    public void runTrustCoarseListTest() {
        test_50_50_workload_coarse_list(5);
    }
}

