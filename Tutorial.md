# Tutorial tasks

## 1. Writing a parametric Concurrent counter

Write a simple test program that accepts a number of threads `n` and instantiates
`n` [CounterThread](app/src/main/java/org/example/CounterThread.java) threads, starts them, and waits for them to finish.

Then run the test program with different values of `n` to see how the counter behaves with different numbers of threads.

[Solution](app/src/main/java/org/example/ParametricCounter.java)

## 2. Writing a test for the Concurrent counter

Write a test program that uses the JMC API to check the correctness of the concurrent counter implementation.
You can use the `CounterThread` class from the previous task to create a test that checks if the counter is incremented correctly by multiple threads.

[Solution](app/src/main/java/org/example/ParametricCounterTest.java)