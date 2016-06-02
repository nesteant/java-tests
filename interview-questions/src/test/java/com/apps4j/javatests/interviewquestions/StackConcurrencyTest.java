package com.apps4j.javatests.interviewquestions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RunWith(BlockJUnit4ClassRunner.class)
public class StackConcurrencyTest {

    private static final Integer TIMEOUT = 10_000;
    public Stack<Integer> stack = new Stack<>();

    ExecutorService service = Executors.newFixedThreadPool(2);

    public StackConcurrencyTest() {
    }

    @Test
    public void test() throws InterruptedException {
        submitAdd();
        submitAdd();
        service.awaitTermination(TIMEOUT * 2L, TimeUnit.MILLISECONDS);
        service.shutdown();
    }

    @Test
    public void testAddRemove() throws InterruptedException {
        submitAdd();
        submitRemoval();
        service.awaitTermination(TIMEOUT * 2L, TimeUnit.MILLISECONDS);
        service.shutdown();
    }

    @Test
    public void testAddRemoveIterate() throws InterruptedException {
        submitAdd();
        submitRemoval();
        submitIterations();
        service.awaitTermination(TIMEOUT * 2L, TimeUnit.MILLISECONDS);
        service.shutdown();
    }

    private void submitAdd() {
        long millis = System.currentTimeMillis();
        service.submit(() -> {
            while (System.currentTimeMillis() - millis < TIMEOUT) {
                int nextInt = ThreadLocalRandom.current().nextInt();
                stack.add(nextInt);
            }
        });
    }

    private void submitRemoval() {
        long millis = System.currentTimeMillis();
        service.submit(() -> {
            while (System.currentTimeMillis() - millis < TIMEOUT) {
                int nextInt = ThreadLocalRandom.current().nextInt(0, 10);
                if (stack.size() > nextInt) {
                    stack.remove(nextInt);
                }
            }
        });
    }

    private void submitIterations() {
        long millis = System.currentTimeMillis();
        service.submit(() -> {
            while (System.currentTimeMillis() - millis < TIMEOUT) {
                for (Integer integer : stack) {
                    if (integer % 2 > 0) {
                        stack.remove(integer);
                    }
                }
            }
        });
    }
}
