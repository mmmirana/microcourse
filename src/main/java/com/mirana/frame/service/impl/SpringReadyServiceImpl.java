package com.mirana.frame.service.impl;

import com.mirana.frame.service.ISpringReadyService;
import com.mirana.frame.utils.log.LogUtils;
import com.mirana.module.Thread.ThreadA;
import com.mirana.module.Thread.ThreadB;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Title：spring初始化完毕后执行的方法
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/823:59
 */
@Service
public class SpringReadyServiceImpl implements ISpringReadyService {

	@Override
	public void init () {
		initProp();
		initDatabase();
		initTask();
	}

	/**
	 * 初始化属性
	 */
	private void initProp () {
		LogUtils.info("初始化属性initProp()");
	}

	/**
	 * 初始化数据库
	 */
	private void initDatabase () {
		LogUtils.info("初始化数据initDatabase()");
	}

	/**
	 * 初始化任务
	 */
	private void initTask () {
		LogUtils.info("初始化任务initTask()");

		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.execute(new ThreadA());
		executorService.execute(new ThreadB());

		// QuartzManager quartzManager = (QuartzManager) SpringCtxUtils.getBean(QuartzManager.class);

		// ScheduleJob joba = new ScheduleJob();
		// joba.setClazz(JobA.class.getName());
		// joba.setJobname("joba");
		// joba.setJobgroup("jobaGroup");
		// joba.setTriggername("jobaTriggername");
		// joba.setTriggergroup("jobaTriggergroup");
		// joba.setCron("*/5 * * * * ?");
		//
		// ScheduleJob jobb = new ScheduleJob("jobb", "jobbgroup", "jobbdesc", 1, "jobbTriggername", "jobbTriggergourp", JobB.class.getName(), "10,33,42 * * * * ?");
		//
		// quartzManager.addJob(joba);
		// quartzManager.addJob(jobb);
	}
}
