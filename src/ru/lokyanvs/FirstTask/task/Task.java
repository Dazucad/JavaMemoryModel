package ru.lokyanvs.FirstTask.task;

import ru.lokyanvs.FirstTask.exception.MyException;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Task<T> {
    private final Callable<? extends T> callable;
    private T result;
    private ReentrantLock lock;
    private MyException exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
        lock = new ReentrantLock();
    }

    public T get() {
        //реализовать метод get() который возвращает результат работы Callable.
        //Выполнение callable должен начинать тот поток, который первый вызвал метод get().
        //Если несколько потоков одновременно вызывают этот метод, то выполнение должно начаться только в одном потоке, а остальные должны ожидать конца выполнения (не нагружая процессор).
        //Если при вызове get() результат уже просчитан, то он должен вернуться сразу, (даже без задержек на вход в синхронизированную область).
        //Если при просчете результата произошел Exception, то всем потокам при вызове get(), надо кидать этот Exception, обернутый в ваш RuntimeException (подходящее название своему ексепшену придумайте сами).
        System.out.println("Поток " + Thread.currentThread().getName() + " вошел в метод get()");
        lock.lock();
        try {
            if (result == null && exception == null)
                synchronized (this) {
                    System.out.println("Поток " + Thread.currentThread().getName() + " вошел в синхронизированную область метода get()");
                    if (result == null)
                        try {
                            //для притормаживания работы
                            System.out.println("Поток " + Thread.currentThread().getName() + " ставится на паузу");
                            Thread.sleep(1000);
                            System.out.println("Поток " + Thread.currentThread().getName() + " произвел ручной расчет");
                            result = callable.call();
                        } catch (Exception e) {
                            exception = new MyException(e);
                        }
                }
        } finally {
            lock.unlock();
        }
        if (exception != null) throw exception;
        return result;
    }
}
