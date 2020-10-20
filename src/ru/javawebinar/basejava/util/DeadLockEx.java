package ru.javawebinar.basejava.util;

public class DeadLockEx {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        new Thread(()->lock(LOCK2, LOCK1)).start();
        new Thread(()->lock(LOCK1, LOCK2)).start();
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
