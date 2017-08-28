package com.chapter1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ��ʾ����
 * **********��ô��ô��������*******************
 * 1������һ���̻߳�ȡ�����
 * 2���������ݿ����������ͽ�����һ�����ݿ�������
 * 3������һ���߳�������ͬʱռ�ö����Դ��������֤ÿ����ֻռ��һ����Դ
 * 4������ʹ�ö�ʱ����lock.trylock��2000�������ʹ���ڲ������ƣ�
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
