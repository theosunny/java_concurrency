package com.chapter1;
/**
 * 
 * @author Administrator
 *
 *测试是否并行线程一定比串行线程快。其实不一定，因为并行线程有切换上下文所消耗的时间
 *当并发执行累加操作不超过百万次时，速度会比串行执行累加操作要
 *慢。那么，为什么并发执行的速度会比串行慢呢？这是因为线程有创建和上下文切换的开销。
 *
 ****************如何减少上下文切换*****************************
 *
减少上下文切换的方法有无锁并发编程、CAS算法、使用最少线程和使用协程。
·无锁并发编程。多线程竞争锁时，会引起上下文切换，所以多线程处理数据时，可以用一
些办法来避免使用锁，如将数据的ID按照Hash算法取模分段，不同的线程处理不同段的数据。
·CAS算法。Java的Atomic包使用CAS算法来更新数据，而不需要加锁。
·使用最少线程。避免创建不需要的线程，比如任务很少，但是创建了很多线程来处理，这
样会造成大量线程都处于等待状态。
·协程：在单线程里实现多任务的调度，并在单线程里维持多个任务间的切换。
 */
public class ConcurrencyTest {
	private static final long count = 10000l;

	public static void main(String[] args) throws InterruptedException {
		concurrency();
		serial();
	}

	private static void concurrency() throws InterruptedException {
		long start = System.currentTimeMillis();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				int a = 0;
				for (long i = 0; i < count; i++) {
					a += 5;
				}
			}
		});
		thread.start();
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		long time = System.currentTimeMillis() - start;
		thread.join();
		System.out.println("concurrency :" + time + "ms,b=" + b);
	}

	private static void serial() {
		long start = System.currentTimeMillis();
		int a = 0;
		for (long i = 0; i < count; i++) {
			a += 5;
		}
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("serial:" + time + "ms,b=" + b + ",a=" + a);
	}
}
