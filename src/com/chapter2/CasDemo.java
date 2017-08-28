package com.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ԭ���Բ�����cas�̰߳�ȫ����
 * @author Administrator
 *
 */
public class CasDemo {
	private int i = 0;
	private AtomicInteger atomicT  = new AtomicInteger(0);
public static void main(String[] args) {
	final CasDemo casDemo = new CasDemo();
	List<Thread> threadList = new ArrayList<Thread>(600);
	long start = System.currentTimeMillis();
	for (int i = 0; i < 10; i++) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int j = 0; j < 10000; j++) {
					casDemo.safeCounter();
					casDemo.count();
				}
				
			}
		});
		threadList.add(thread);
	}
	for (Thread thread : threadList) {
		thread.start();
	}
	// �ȴ������߳�ִ�����
	for (Thread thread : threadList) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	System.out.println("��ԭ���Բ���"+casDemo.i);
	System.out.println("ԭ���Բ�����"+casDemo.atomicT.get());
	System.out.println("ִ��ʱ�䣺"+(System.currentTimeMillis() - start));
}
//�̰߳�ȫ ����
public void safeCounter(){
   for(;;){
	   int i = atomicT.get();
	   boolean succ = atomicT.compareAndSet(i, ++i);
	   if (succ) {
		break;
	}
   }	
}
//�̲߳���ȫ
public void count(){
	i++;
}
}
