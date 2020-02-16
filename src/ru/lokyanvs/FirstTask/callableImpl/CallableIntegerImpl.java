package ru.lokyanvs.FirstTask.callableImpl;

import java.util.concurrent.Callable;

public class CallableIntegerImpl implements Callable<Integer> {
    private int a, b;

    public CallableIntegerImpl(int a,int b) {
        this.a=a;
        this.b=b;
    }

    @Override
    public Integer call() throws Exception {
        //для притормаживания работы
        Thread.sleep(1000);
        //throw new Exception("что-то пошло не так");
        return a*b;
    }
}
