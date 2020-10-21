package ru.javawebinar.basejava;

public class DeadLockEx {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        new Thread(()->lock(LOCK2, LOCK1)).start();
        new Thread(()->lock(LOCK1, LOCK2)).start();
    }

    public static void lock(Object lock1, Object lock2) {
        System.out.println(Thread.currentThread().getName() + ": " + "Попытка захватить монитор " + lock1);
        synchronized (lock1){
            System.out.println(Thread.currentThread().getName() + ": " + "Монитор " + lock1 + " захвачен");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": " + "Попытка захватить монитор " + lock2);
            synchronized (lock2){
                System.out.println(Thread.currentThread().getName() + ": " + "Мониторы " + lock1 + lock2 + " захвачены");
            }
        }
    }
}
