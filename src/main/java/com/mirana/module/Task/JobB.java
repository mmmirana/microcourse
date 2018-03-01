package com.mirana.module.Task;

import com.mirana.frame.utils.date.DatePattern;
import com.mirana.frame.utils.date.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Title：JobB
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/269:41
 */
public class JobB implements Job {

	@Override
	public void execute (JobExecutionContext jobExecutionContext) throws JobExecutionException {
		System.out.println(DateUtils.format(new Date(), DatePattern.DATETIME_SLASH) + " JobB is running...");
	}
}
