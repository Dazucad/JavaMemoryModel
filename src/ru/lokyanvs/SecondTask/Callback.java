package ru.lokyanvs.SecondTask;

public class Callback implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Запущено задание Callback");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Конец задания Callback");
        }
    }
}
