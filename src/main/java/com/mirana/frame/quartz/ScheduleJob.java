package com.mirana.frame.quartz;

import com.mirana.frame.base.model.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Title：调度任务
 *
 * @CreatedBy Mirana
 * @DateTime 2018/2/2416:39
 */
public class ScheduleJob extends BaseModel {

	private String  jobname;
	private String  jobgroup;
	private String  jobdesc;
	private Integer jobstatus;

	private String triggername;
	private String triggergroup;
	private String clazz;
	private String cron;


	public ScheduleJob () {
		super();
	}

	public ScheduleJob (String jobname, String jobgroup, String jobdesc, Integer jobstatus, String triggername, String triggergroup, String clazz, String cron) {
		this.jobname = jobname;
		this.jobgroup = jobgroup;
		this.jobdesc = jobdesc;
		this.jobstatus = jobstatus;
		this.triggername = triggername;
		this.triggergroup = triggergroup;
		this.clazz = clazz;
		this.cron = cron;
	}

	public String getJobname () {
		return jobname;
	}

	public void setJobname (String jobname) {
		this.jobname = jobname;
	}

	public String getJobgroup () {
		return jobgroup;
	}

	public void setJobgroup (String jobgroup) {
		this.jobgroup = jobgroup;
	}

	public String getJobdesc () {
		return jobdesc;
	}

	public void setJobdesc (String jobdesc) {
		this.jobdesc = jobdesc;
	}

	public Integer getJobstatus () {
		return jobstatus;
	}

	public void setJobstatus (Integer jobstatus) {
		this.jobstatus = jobstatus;
	}

	public String getTriggername () {
		return triggername;
	}

	public void setTriggername (String triggername) {
		this.triggername = triggername;
	}

	public String getTriggergroup () {
		return triggergroup;
	}

	public void setTriggergroup (String triggergroup) {
		this.triggergroup = triggergroup;
	}

	public String getClazz () {
		return clazz;
	}

	public void setClazz (String clazz) {
		this.clazz = clazz;
	}

	public String getCron () {
		return cron;
	}

	public void setCron (String cron) {
		this.cron = cron;
	}

	@Override
	public String toString () {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
