package ru.lokyanvs.SecondTask;

public class Task implements Runnable {
    private static long sleepTime = 0;
    private long sleep;

    @Override
    public void run() {
        try {
            System.out.println("Выполняется задание Task в потоке " + Thread.currentThread().getName());
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (sleep == 1000) {
                System.out.println("Exception в задании Task в потоке " + Thread.currentThread().getName());
                throw new RuntimeException("Ошибка");
            }
            System.out.println("Конец задания Task в потоке " + Thread.currentThread().getName());
        }
    }

    Task() {
        sleep = sleepTime + 1000;
        sleepTime += sleep;
    }
}
