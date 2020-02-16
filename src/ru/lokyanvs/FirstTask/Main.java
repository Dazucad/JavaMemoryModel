package ru.lokyanvs.FirstTask;

import ru.lokyanvs.FirstTask.callableImpl.CallableIntegerImpl;
import ru.lokyanvs.FirstTask.task.Task;

public class Main {
    private static final int threadCount = 10;

    public static void main(String[] args) {
        CallableIntegerImpl callable = new CallableIntegerImpl(2, 3);
        Task<Integer> task = new Task<>(callable);
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> System.out.println(task.get()), Integer.toString(i));
            threads[i].start();
        }
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}
