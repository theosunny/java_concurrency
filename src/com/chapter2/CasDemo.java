package com.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性操作，cas线程安全测试
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
	// 等待所有线程执行完成
	for (Thread thread : threadList) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	System.out.println("非原子性操作"+casDemo.i);
	System.out.println("原子性操作："+casDemo.atomicT.get());
	System.out.println("执行时间："+(System.currentTimeMillis() - start));
}
//线程安全 计数
public void safeCounter(){
   for(;;){
	   int i = atomicT.get();
	   boolean succ = atomicT.compareAndSet(i, ++i);
	   if (succ) {
		break;
	}
   }	
}
//线程不安全
public void count(){
	i++;
}
}
