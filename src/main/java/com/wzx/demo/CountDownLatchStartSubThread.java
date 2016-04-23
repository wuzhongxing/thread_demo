package com.wzx.demo;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author zhongxing.wu
 * @since 2016年4月20日
 */
public class CountDownLatchStartSubThread {

	public static void main(String[] args) {
		final CountDownLatch startSignal = new CountDownLatch(1);
		int count = 2;
		final CountDownLatch doneSignal = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			new Thread(new Worker(startSignal, doneSignal), "worker-latch-" + i).start();
		}

		try {
			System.out.println("Main doing");
			Thread.sleep(3000);
			startSignal.countDown();
			System.out.println("startSignal.countDown()触发子线程统一行动...");

			System.out.println("doneSignal.await()触发主线程等待线程执行完成...");
			doneSignal.await();
			System.out.println("线程执行完毕");
			System.out.println("主线程继续");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static final class Worker implements Runnable {
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		private Worker(CountDownLatch start, CountDownLatch done) {
			this.startSignal = start;
			this.doneSignal = done;
		}

		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " startSignal.await阻塞等待主线程发起信号");
				startSignal.await();
				System.out.println(Thread.currentThread().getName() + " doing...");
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName()
						+ " done and doneSignal.countDown() 触发减少latch count");
				doneSignal.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
