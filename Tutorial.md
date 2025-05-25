# Tutorial tasks

## Disclaimers and guidelines

- Currently, ReentrantLock is the only synchronization primitive supported by the JMC API. Do not use other synchronization primitives like `synchronized`.
- When writing threads, use the paradigm of extending the `Thread` class and overriding the `run()` method. Do not use `Runnable` or `Callable` interfaces.
- When a class should not be instrumented to test, annotate it with `@JmcIgnoreInstrumentation` flag.
- You can update the `numIterations` parameter in each test to control the number of iterations for the test.
- Each test can be parameterized with a debug flag, `debug=true` and when set, the executions graphs (if running with trust strategy) will be stored in `build/test-results/jmc-report`. The graphs can be visualized using the following command:

```bash
./visualize_graphs.sh
````
- Running tests on the command line can be done with the following command:

```bash
./gradlew :app:test --tests "org.example.<className>.<methodName>"
```

## 1. Writing a parametric Concurrent counter

Write a simple test program that accepts a number of threads `n` and instantiates
`n` [CounterThread](app/src/main/java/org/example/CounterThread.java) threads, starts them, and waits for them to finish.

Then run the test program with different values of `n` to see how the counter behaves with different numbers of threads.

[Solution](app/src/main/java/org/example/ParametricCounter.java)

## 2. Writing a test for the Concurrent counter

Write a test program that uses the JMC API to check the correctness of the concurrent counter implementation.
You can use the `CounterThread` class from the previous task to create a test that checks if the counter is incremented correctly by multiple threads.

[Solution](app/src/test/java/org/example/ParametricCounterTest.java)

## 3. Writing test harness for Coarse and Fine Grained Counter Tests

Write a test Harness that uses the [CoarseList](app/src/main/java/org/example/list/coarse/CoarseList.java) and [FineList](app/src/main/java/org/example/list/fine/FineList.java) classes to test the correctness of the concurrent list implementations.
The test should accept a parameter `n`. Subsequently, it should create `n/2` [InsertionThreads](app/src/main/java/org/example/list/InsertionThread.java) and `n/2` [DeletionThread](app/src/main/java/org/example/list/DeletionThread.java) threads, start them, and wait for them to finish.

Finally, the test should check if the list is in a consistent state after all threads have finished.

[Solution](app/src/test/java/org/example/CoarseListTest.java)


## 4. Running a test with Random and measuring coverage of Execution Graphs

The [MeasureCoverageTest](app/src/test/java/org/example/MeasureCoverageTest.java) defines a test that measures the coverage of execution graphs for the concurrent counter implementation.

Run the test using the following command:

```bash
./gradlew :app:test --tests "org.example.MeasureCoverageTest" 
```

## 5. Write a coverage test for Coarse List

Define coverage tests for the CoarseList and run with Random to see the number of execution graphs covered.

[Solution](app/src/test/java/org/example/CoarseListCoverageTest.java)

## 6. Write a custom strategy to test programs with.

Write a custom strategy to test programs with. For example [WeightedRandomStrategy](app/src/main/java/org/example/strategies/WeightedRandomStrategy.java) defines a boilerplate for a custom strategy that can be used to test programs with a weighted random approach.

Given a threshold `n`, the strategy will select from a given set of available tasks based on their weights (initially `n`) and decrement every time they observe an event for that task. When the weights of all tasks reach zero, the strategy will revert to a random selection of tasks.
