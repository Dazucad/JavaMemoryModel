package ru.lokyanvs.SecondTask;

import ru.lokyanvs.SecondTask.interfaceImpl.ExecutionManagerImpl;
import ru.lokyanvs.SecondTask.interfaces.Context;
import ru.lokyanvs.SecondTask.interfaces.ExecutionManager;

public class Main {
    public static void main(String[] args) throws Exception{
        ExecutionManager em = new ExecutionManagerImpl();
        Context context = em.execute(new Callback(), new Task(), new Task(), new Task(),new Task(),new Task());
        Thread.sleep(3500);
        context.interrupt();
        while (!context.isFinished());
        System.out.println("Потоков с Exception: "+context.getFailedTaskCount()+
                "\nПотоков прервано: "+context.getInterruptedTaskCount()+
                "\nПотоков выполнено: "+context.getCompletedTaskCount());
    }
}
