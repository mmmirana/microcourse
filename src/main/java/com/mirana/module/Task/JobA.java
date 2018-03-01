package com.mirana.module.Task;

import com.mirana.frame.utils.date.DatePattern;
import com.mirana.frame.utils.date.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Title：JobA
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/269:40
 */
public class JobA implements Job {
	@Override
	public void execute (JobExecutionContext jobExecutionContext) throws JobExecutionException {
		System.out.println(DateUtils.format(new Date(), DatePattern.DATETIME_SLASH) + " JobA is running...");
	}
}
