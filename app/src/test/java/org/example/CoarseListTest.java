package org.example;

import org.example.list.DeletionThread;
import org.example.list.InsertionThread;
import org.example.list.Set;
import org.example.list.coarse.CoarseList;
import org.mpisws.jmc.annotations.JmcCheck;
import org.mpisws.jmc.annotations.JmcCheckConfiguration;
import org.mpisws.jmc.util.concurrent.JmcThread;

import java.util.ArrayList;
import java.util.List;

public class CoarseListTest {

    private void test_50_50_workload_coarse_list(int NUM_THREADS) {

        int NUM_INSERTIONS = (int) Math.ceil(NUM_THREADS / 2.0);
        int NUM_DELETIONS = (int) Math.floor(NUM_THREADS / 2.0);

        int[] arr = new int[NUM_INSERTIONS];
        for (int i = 0; i < NUM_INSERTIONS; i++) {
            arr[i] = i + 1; // Fixing input data
        }

        Set set = new CoarseList();

        List<JmcThread> insertionThreads = new ArrayList<>(NUM_INSERTIONS);
        for (int i = 0; i < NUM_INSERTIONS; i++) {
            int item = arr[i];
            InsertionThread ithread = new InsertionThread(set, item);
            insertionThreads.add(ithread);
        }

        List<JmcThread> deleteThreads = new ArrayList<>(NUM_DELETIONS);
        for (int i = 0; i < NUM_DELETIONS; i++) {
            int item = arr[i];
            DeletionThread dthread = new DeletionThread(set, item);
            deleteThreads.add(dthread);
        }

        for (int i = 0; i < NUM_INSERTIONS; i++) {
            insertionThreads.get(i).start();
        }

        for (int i = 0; i < NUM_DELETIONS; i++) {
            deleteThreads.get(i).start();
        }

        for (int i = 0; i < NUM_INSERTIONS; i++) {
            try {
                insertionThreads.get(i).join1();
            } catch (InterruptedException e) {

            }
        }

        for (int i = 0; i < NUM_DELETIONS; i++) {
            try {
                deleteThreads.get(i).join1();
            } catch (InterruptedException e) {

            }
        }
    }

    // Running with JMC using the default configuration.
    @JmcCheck
    @JmcCheckConfiguration(numIterations = 100)
    public void runRandomCoarseListTest() {
        test_50_50_workload_coarse_list(6);
    }

    @JmcCheck
    @JmcCheckConfiguration(strategy = "trust", numIterations = 130)
    public void runTrustCoarseListTest() {
        test_50_50_workload_coarse_list(5);
    }
}

