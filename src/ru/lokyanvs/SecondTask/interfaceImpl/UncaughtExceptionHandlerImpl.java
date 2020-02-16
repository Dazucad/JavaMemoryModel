package ru.lokyanvs.SecondTask.interfaceImpl;

import java.util.HashMap;
import java.util.Map;

public class UncaughtExceptionHandlerImpl implements Thread.UncaughtExceptionHandler {
    private final Map<Thread, Throwable> failedTasks = new HashMap<>();

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!(throwable instanceof InterruptedException))
            failedTasks.put(thread, throwable);
    }

    boolean isFailed(Thread task) {
        return failedTasks.containsKey(task);
    }
}
