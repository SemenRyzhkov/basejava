package ru.javawebinar.basejava.util;

public class DeadLockEx {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        thread1.start();
        lock(LOCK1, LOCK2);
    }

    public static void lock(Object lock1, Object lock2){
        System.out.println("Попытка захватить монитор " + lock1);
        synchronized (lock1){
            System.out.println("Монитор " + lock1 + " захвачен");
            System.out.println("Попытка захватить монитор " + lock2);
            synchronized (lock2){
                System.out.println("Мониторы " + lock1 + lock2 + " захвачены");
            }
        }
    }
}

class Thread1 extends Thread{
    public void run(){
        DeadLockEx.lock(DeadLockEx.LOCK2, DeadLockEx.LOCK1);
    }
}
