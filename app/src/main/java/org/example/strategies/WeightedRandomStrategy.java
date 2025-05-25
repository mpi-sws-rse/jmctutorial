package org.example.strategies;

import org.mpisws.jmc.annotations.JmcIgnoreInstrumentation;
import org.mpisws.jmc.runtime.HaltCheckerException;
import org.mpisws.jmc.runtime.RuntimeEvent;
import org.mpisws.jmc.runtime.SchedulingChoice;
import org.mpisws.jmc.strategies.TrackActiveTasksStrategy;

import java.util.*;

@JmcIgnoreInstrumentation
public class WeightedRandomStrategy extends TrackActiveTasksStrategy {

    private int maxPerTask;
    private Random random;

    private Map<Long, Integer> taskWeights = new HashMap<>();

    public WeightedRandomStrategy(int maxPerTask) {
        this.maxPerTask = maxPerTask;
        this.random = new Random();
        this.taskWeights = new HashMap<>();
    }

    @Override
    public void updateEvent(RuntimeEvent event) {
        // Handle the event as needed.
        super.updateEvent(event);

        Long taskId = event.getTaskId();

        if (!taskWeights.containsKey(taskId)) {
            taskWeights.put(taskId, maxPerTask);
        }
        Integer taskWeight = taskWeights.get(taskId);
        if (taskWeight > 0) {
            taskWeights.put(taskId, taskWeight - 1);
        }
    }

    @Override
    public SchedulingChoice<?> nextTask() {

        Set<Long> activeTasks = getActiveTasks();

        List<Integer> weights = new ArrayList<>();
        for (Long taskId : activeTasks) {
            Integer weight = taskWeights.getOrDefault(taskId, maxPerTask);
            weights.add(weight);
        }

        Long taskSelected = null;

        // Fill in the logic to select a task based on weights

        throw HaltCheckerException.error("WeightedRandomStrategy: nextTask not implemented yet");

        // return SchedulingChoice.task(taskSelected);
    }
}
