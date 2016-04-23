package com.wzx.demo;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author zhongxing.wu
 * @since 2016年4月20日
 */
public class CountDownLatchBlockMainThread {

	public static void main(String[] args) {
		int count = 2;
		final CountDownLatch latch = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			new Thread(new Worker(latch), "worker-latch-" + i).start();
		}

		try {
			System.out.println("CountDownLatch.await()触发主线程等待线程执行...");
			latch.await();
			System.out.println("线程执行完毕");
			System.out.println("主线程继续");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static final class Worker implements Runnable {
		private final CountDownLatch latch;

		private Worker(CountDownLatch latch) {
			this.latch = latch;
		}

		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + "doing");
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName() + "done and CountDownLatch.countDown()触发减少latch count");
				latch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
