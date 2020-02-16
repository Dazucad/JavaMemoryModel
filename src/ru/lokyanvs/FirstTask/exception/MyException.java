package ru.lokyanvs.FirstTask.exception;

public class MyException extends RuntimeException {
    public MyException(){
        super("Произошла ошибка");
    }

    public MyException(Exception cause){
        super(cause);
    }
}
