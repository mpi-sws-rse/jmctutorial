# Using the Java Model Checker

## Pre-requisites

- Java 17 or later
- Gradle 8.4 or later

### Installing JMC

JMC project should be downloaded from the [GitHub repository](https://github.com/mpi-sws-rse/jmc) and built as follows

```bash
git clone https://github.com/mpi-sws-rse/jmc.git
cd jmc
./gradlew clean
./gradlew :core:publish
```

This will build the JMC core and publish it to your local Maven repository.

## Running Counter Example

The repository contains a sample project that uses JMC to check concurrent counter implementation. 
To run the example, follow these steps:

```bash
./gradlew clean
./gradlew test
```
