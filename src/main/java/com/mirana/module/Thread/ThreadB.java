package com.mirana.module.Thread;

import com.mirana.frame.utils.log.LogUtils;

/**
 * Title：ThreadB
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/259:12
 */
public class ThreadB implements Runnable {
	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run () {
		LogUtils.info("ThreadB is ready, it will run after 10 seconds...");
		try {
			Thread.currentThread().sleep(10 * 1000);
			LogUtils.info("ThreadB is running...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
