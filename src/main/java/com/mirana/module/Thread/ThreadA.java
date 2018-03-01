package com.mirana.module.Thread;

import com.mirana.frame.utils.log.LogUtils;

/**
 * Title：ThreadA
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/259:11
 */
public class ThreadA implements Runnable {
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
		LogUtils.info("ThreadA is ready, it will run after 5 seconds...");
		try {
			Thread.currentThread().sleep(5 * 1000);
			LogUtils.info("ThreadA is running...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
