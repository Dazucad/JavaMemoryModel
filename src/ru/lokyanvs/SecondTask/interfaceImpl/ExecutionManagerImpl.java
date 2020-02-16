package ru.lokyanvs.SecondTask.interfaceImpl;

import ru.lokyanvs.SecondTask.interfaces.Context;
import ru.lokyanvs.SecondTask.interfaces.ExecutionManager;

import java.util.ArrayList;
import java.util.List;

public class ExecutionManagerImpl implements ExecutionManager {
    private List<Thread> tasks = new ArrayList<>();
    private Runnable callback;

    //Метод execute – это неблокирующий метод, который сразу возвращает объект Context.
    //Метод execute принимает массив тасков,
    //это задания которые ExecutionManager должен выполнять параллельно (в вашей реализации пусть будет в своем пуле потоков).
    //После завершения всех тасков должен выполниться callback (ровно 1 раз).
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        this.callback = callback;
        UncaughtExceptionHandlerImpl exceptionHandler = new UncaughtExceptionHandlerImpl();
        for (Runnable task : tasks) {
            Thread newTask = new Thread(task);
            newTask.setUncaughtExceptionHandler(exceptionHandler);
            this.tasks.add(newTask);
        }
        Context context = new ContextImpl(this.tasks, exceptionHandler);
        callbackRun(context);
        return context;
    }

    private void callbackRun(Context context) {
        new Thread(() -> {
            while (!context.isFinished());
            callback.run();
        }).start();
    }
}
