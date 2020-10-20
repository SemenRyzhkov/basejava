package ru.javawebinar.basejava.util;

public class DeadLockEx {
    public static final Object LOCK1 = new Object();
    public static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }
}

class Thread1 extends Thread{
    public void run(){
        System.out.println("Thread1: попытка захватить монитор LOCK1");
        synchronized (DeadLockEx.LOCK1){
            System.out.println("Thread1: монитор LOCK1 захвачен");
            System.out.println("Thread1: попытка захватить монитор LOCK2");
            synchronized (DeadLockEx.LOCK2){
                System.out.println("Thread1: монитор LOCK2 захвачен");
            }
        }
    }
}

class Thread2 extends Thread{
    public void run(){
        System.out.println("Thread2: попытка захватить монитор LOCK2");
        synchronized (DeadLockEx.LOCK2){
            System.out.println("Thread2: монитор LOCK2 захвачен");
            System.out.println("Thread2: попытка захватить монитор LOCK1");
            synchronized (DeadLockEx.LOCK1){
                System.out.println("Thread1: монитор LOCK1 захвачен");
            }
        }
    }
}
