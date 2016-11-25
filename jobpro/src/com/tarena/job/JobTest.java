package com.tarena.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobTest {
	
	/**
	 * 定时任务，第25分钟执行一次，获取有效tokenid，防止失效。
	 */
	static{
		System.out.println("开始启动定时任务");
		try {
			SchedulerFactory sFactory=new StdSchedulerFactory();
			Scheduler scheduler = sFactory.getScheduler();
			scheduler.start();
			
			JobDetail jobDetail = JobBuilder.newJob(BrTokenIdTask.class)
					.withIdentity("brjob", "job-group").build();
			CronTrigger cronTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity("cronTrigger", "trigger-group")
					.withSchedule(
							CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
					.build();

			scheduler.scheduleJob(jobDetail, cronTrigger);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 定时任务执行内容
	 * @author dell
	 */
	class BrTokenIdTask implements Job {
		public void execute(JobExecutionContext arg0)
				throws JobExecutionException {
			System.out.println("定时任务开始执行");
		}
	}
	
	
	public static void main(String[] args) {
		new Thread().start();
	}
	
	
}
