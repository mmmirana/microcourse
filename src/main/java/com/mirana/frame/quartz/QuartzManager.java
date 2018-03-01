package com.mirana.frame.quartz;

import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.log.LogUtils;
import org.quartz.*;

import javax.annotation.Resource;

/**
 * Title：quartz任务调度管理器
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/2415:59
 */
public class QuartzManager {

	@Resource(name = "scheduler")
	private Scheduler scheduler;


	public static final class QuartzManagerHolder {
		public static final QuartzManager QuartzManager_INSTANCE = new QuartzManager();
	}

	/**
	 * 单例获取QuartzManager实例
	 *
	 * @return
	 */
	public static QuartzManager getInstance () {
		return QuartzManagerHolder.QuartzManager_INSTANCE;
	}


	/**
	 * 添加任务
	 *
	 * @param scheduleJob
	 */
	public void addJob (ScheduleJob scheduleJob) {

		try {
			// 任务
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(scheduleJob.getClazz()))
				// jobname jobgroup
				.withIdentity(scheduleJob.getJobname(), scheduleJob.getJobgroup())
				// build
				.build();

			// 触发器
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			// 触发器名称、组名称
			triggerBuilder.withIdentity(scheduleJob.getTriggername(), scheduleJob.getTriggergroup());
			// 触发时间
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCron()));
			// 立即执行
			triggerBuilder.startNow();
			// 创建Trigger对象
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();

			scheduler.scheduleJob(jobDetail, trigger);

			LogUtils.info("添加任务" + scheduleJob + "成功！");

			// 立即运行
			if (scheduler.isShutdown()) {
				scheduler.start();
			}

		} catch (Exception e) {
			throw new UtilsException("添加任务" + scheduleJob + "失败！", e);
		}

	}

	/**
	 * 移除任务
	 *
	 * @param scheduleJob
	 */
	public void removeJob (ScheduleJob scheduleJob) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTriggername(), scheduleJob.getTriggergroup());

			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			scheduler.deleteJob(JobKey.jobKey(scheduleJob.getJobname(), scheduleJob.getJobgroup()));// 删除任务


			LogUtils.info("移除任务" + scheduleJob + "成功！");
		} catch (Exception e) {
			throw new UtilsException("移除任务" + scheduleJob + "失败！", e);
		}
	}

	/**
	 * 启动所有任务
	 */
	public void startJobs () {
		try {
			scheduler.start();
			LogUtils.info("启动所有任务成功！");
		} catch (Exception e) {
			throw new UtilsException("启动所有任务失败！", e);
		}
	}

	/**
	 * 关闭所有任务
	 */
	public void stopJobs () {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
			LogUtils.info("关闭所有任务成功！");
		} catch (Exception e) {
			throw new UtilsException("关闭所有任务失败！", e);
		}
	}

	public Scheduler getScheduler () {
		return scheduler;
	}

	public void setScheduler (Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}
