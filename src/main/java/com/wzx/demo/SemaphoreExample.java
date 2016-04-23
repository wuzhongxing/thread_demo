package com.wzx.demo;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author zhongxing.wu
 * @since 2016年4月22日
 */
public class SemaphoreExample {
	public static void main(String[] args) {
		int N = 8; // 线程总数
		Semaphore semaphore = new Semaphore(3); // 资源量
		for (int i = 0; i < N; i++) {
			new Thread(new Worker(i, semaphore), "Semaphore").start();
		}
	}

	private static class Worker extends Thread {
		private int num;
		private Semaphore semaphore;

		public Worker(int num, Semaphore semaphore) {
			this.num = num;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println(Thread.currentThread().getName() + this.num + "获得资源...");
				// do sth
				Thread.sleep(new Random().nextInt(5000));
				System.out.println(Thread.currentThread().getName() + this.num + "释放资源");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaphore.release();
			}
		}
	}
}
