package com.chapter1;
/**
 * 
 * @author Administrator
 *
 *�����Ƿ����߳�һ���ȴ����߳̿졣��ʵ��һ������Ϊ�����߳����л������������ĵ�ʱ��
 *������ִ���ۼӲ��������������ʱ���ٶȻ�ȴ���ִ���ۼӲ���Ҫ
 *������ô��Ϊʲô����ִ�е��ٶȻ�ȴ������أ�������Ϊ�߳��д������������л��Ŀ�����
 *
 ****************��μ����������л�*****************************
 *
�����������л��ķ���������������̡�CAS�㷨��ʹ�������̺߳�ʹ��Э�̡�
������������̡����߳̾�����ʱ���������������л������Զ��̴߳�������ʱ��������һ
Щ�취������ʹ�������罫���ݵ�ID����Hash�㷨ȡģ�ֶΣ���ͬ���̴߳���ͬ�ε����ݡ�
��CAS�㷨��Java��Atomic��ʹ��CAS�㷨���������ݣ�������Ҫ������
��ʹ�������̡߳����ⴴ������Ҫ���̣߳�����������٣����Ǵ����˺ܶ��߳���������
������ɴ����̶߳����ڵȴ�״̬��
��Э�̣��ڵ��߳���ʵ�ֶ�����ĵ��ȣ����ڵ��߳���ά�ֶ���������л���
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
