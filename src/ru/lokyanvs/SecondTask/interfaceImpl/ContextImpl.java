package ru.lokyanvs.SecondTask.interfaceImpl;

import ru.lokyanvs.SecondTask.interfaces.Context;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextImpl implements Context {
    private final List<Thread> tasks;
    private final UncaughtExceptionHandlerImpl exceptionHandler;
    private final Set<Thread> interruptedTasks = new HashSet<>();

    ContextImpl(List<Thread> tasks, UncaughtExceptionHandlerImpl exceptionHandler) {
        this.tasks = tasks;
        this.exceptionHandler = exceptionHandler;
        delayedTaskStart();
    }

    //запуск заданий
    private void delayedTaskStart() {
        new Thread(() -> {
            for (Thread task : tasks) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (interruptedTasks) {
                    if (!interruptedTasks.contains(task))
                        task.start();
                }
            }
        }).start();
    }

    //Метод getCompletedTaskCount() возвращает количество тасков, которые на текущий момент успешно выполнились.
    @Override
    public int getCompletedTaskCount() {
        int count = 0;
        for (Thread task : tasks)
            synchronized (interruptedTasks) {
                if (!task.isAlive() &&task.getState()!= Thread.State.NEW&& !exceptionHandler.isFailed(task) && !interruptedTasks.contains(task)) count++;
            }
        return count;
    }

    //Метод getFailedTaskCount() возвращает количество тасков, при выполнении которых произошел Exception.
    @Override
    public int getFailedTaskCount() {
        int count = 0;
        for (Thread task : tasks) if (exceptionHandler.isFailed(task)) count++;
        return count;
    }

    //Метод interrupt() отменяет выполнения тасков, которые еще не начали выполняться.
    @Override
    public void interrupt() {
        System.out.println("Прерывание незапущенных заданий");
        for (Thread task : tasks)
            synchronized (interruptedTasks) {
                if (task.getState() == Thread.State.NEW)
                    interruptedTasks.add(task);
            }
    }

    //Метод getInterruptedTaskCount() возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода).
    @Override
    public int getInterruptedTaskCount() {
        int count;
        synchronized (interruptedTasks) {
            count = interruptedTasks.size();
        }
        return count;
    }

    //Метод isFinished() вернет true, если все таски были выполнены или отменены, false в противном случае.
    @Override
    public boolean isFinished() {
        return (getCompletedTaskCount() + getFailedTaskCount() + getInterruptedTaskCount()) == tasks.size();
    }
}
