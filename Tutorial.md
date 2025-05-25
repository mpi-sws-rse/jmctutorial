# Tutorial tasks

## 1. Writing a parametric Concurrent counter

Write a simple test program that accepts a number of threads `n` and instantiates
`n` [CounterThread](app/src/main/java/org/example/CounterThread.java) threads, starts them, and waits for them to finish.

Then run the test program with different values of `n` to see how the counter behaves with different numbers of threads.

[Solution](app/src/main/java/org/example/ParametricCounter.java)

## 2. Writing a test for the Concurrent counter

Write a test program that uses the JMC API to check the correctness of the concurrent counter implementation.
You can use the `CounterThread` class from the previous task to create a test that checks if the counter is incremented correctly by multiple threads.

[Solution](app/src/test/java/org/example/ParametricCounterTest.java)

## 3. Running Tests

Run the test you wrote for the Concurrent counter.
To run any tests, you can use the following command:
```bash
./gradlew :app:test --tests "classPath.methodName"
```

For running the test you wrote in the previous task, use:
```bash
./gradlew :app:test --tests "org.example.ParametricCounterTest.runTrustParametricCounterTest"
```