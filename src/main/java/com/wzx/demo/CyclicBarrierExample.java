package com.wzx.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author zhongxing.wu
 * @since 2016年4月20日
 */
public class CyclicBarrierExample {
	public static void main(String[] args) {
		int N = 4;
		CyclicBarrier barrier = new CyclicBarrier(N, new Runnable() {
			@Override
			public void run() {
				System.out.println("当前线程" + Thread.currentThread().getName());
			}
		});

		for (int i = 0; i < N; i++) {
			new Thread(new Worker(barrier), "barrierworker-" + i).start();
		}
		
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < N; i++) {
			new Thread(new Worker(barrier), "barrierworker2-" + i).start();
		}
	}

	private static class Worker implements Runnable {
		private CyclicBarrier cyclicBarrier;

		public Worker(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "正在处理...");
			try {
				Thread.sleep(5000); // 以睡眠来模拟写入数据操作
				System.out.println(Thread.currentThread().getName() + "done，wait other");
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("所有线程处理完毕继续");
		}
	}
}
