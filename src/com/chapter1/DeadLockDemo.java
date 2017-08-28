package com.chapter1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示死锁
 * **********那么怎么避免死锁*******************
 * 1、避免一个线程获取多个锁
 * 2、对于数据库锁，加锁和解锁在一个数据库链接里
 * 3、避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源
 * 4、尝试使用定时锁，lock.trylock（2000）来替代使用内部锁机制；
 * //		Lock lock = new ReentrantLock();
//		lock.tryLock()
 * @author Administrator
 * 
 */
public class DeadLockDemo {
	private static String A = "A";
	private static String B = "B";

	public static void main(String[] args) {
		new DeadLockDemo().deadLock();
	}

	public void deadLock() {
		Thread thread1 = new Thread(new Runnable(){

			@Override
			public void run() {
				 synchronized (A) {
					 try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 System.out.println(Thread.currentThread().getName());
						synchronized (B) {
							System.out.println(1);
						}
					}
				
			}
			
		});
		thread1.start();
		Thread thread2 = new Thread(new Runnable(){

			@Override
			public void run() {
			
				synchronized (B) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (A) {
						System.out.println(2);
					}
				}
			}
			
		});
		thread2.start();
	}
}
