package ru.lokyanvs.SecondTask.interfaces;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
