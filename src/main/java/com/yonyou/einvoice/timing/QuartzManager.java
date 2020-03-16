package com.yonyou.einvoice.timing;

import java.util.Date;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 任务调度处理
 *
 * @author qiwen
 */
@Configuration
@Slf4j
public class QuartzManager {

  // 任务调度
  @Resource
  private Scheduler scheduler;

  @Value("${cron}")
  private String cron;

  /**
   * 开始执行所有任务
   */
  public void startJob() throws SchedulerException {
    log.info("开始执行所有任务");
    startJob1();
    scheduler.start();
  }

  /**
   * 获取Job信息
   */
  public String getJobInfo(String name, String group) throws SchedulerException {
    log.info("获得Job信息,Job的name:" + name + " group:" + group);
    String ss=null;
    try {
      TriggerKey triggerKey = new TriggerKey(name, group);
      CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
      ss = String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
          scheduler.getTriggerState(triggerKey).name());
    }catch (NullPointerException e){
      return "没有查询到 Job的name："+name+" group:" + group+" 的任务的信息";
    }
    return ss;
  }

  /**
   * 修改某个任务的执行时间
   */
  public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
    try {
      log.info("修改某个任务的执行时间 name:" + name + " group:" + group + " cron:" + cron);
      Date date = null;
      TriggerKey triggerKey = new TriggerKey(name, group);
      CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
      String oldTime = cronTrigger.getCronExpression();
      if (!oldTime.equalsIgnoreCase(cron)) {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
            .withSchedule(cronScheduleBuilder).build();
        date = scheduler.rescheduleJob(triggerKey, trigger);
      }
      return date != null;
    }catch (RuntimeException e){
      log.info("Cron表达式不合法");
      return false;
    }
  }

  /**
   * 暂停所有任务
   */
  public void pauseAllJob() throws SchedulerException {
    log.info("暂停所有任务");
    scheduler.pauseAll();
  }

  /**
   * 暂停某个任务
   */
  public void pauseJob(String name, String group) throws SchedulerException {
    log.info("暂停某个任务 name:" + name + " group:" + group);
    JobKey jobKey = new JobKey(name, group);
    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
    if (jobDetail == null) {
      return;
    }
    scheduler.pauseJob(jobKey);
  }

  /**
   * 恢复所有任务
   */
  public void resumeAllJob() throws SchedulerException {
    log.info("恢复所有任务");
    scheduler.resumeAll();
  }

  /**
   * 恢复某个任务
   */
  public void resumeJob(String name, String group) throws SchedulerException {
    log.info("恢复某个任务 name:" + name + " group:" + group);
    JobKey jobKey = new JobKey(name, group);
    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
    if (jobDetail == null) {
      return;
    }
    scheduler.resumeJob(jobKey);
  }

  /**
   * 删除某个任务
   */
  public void deleteJob(String name, String group) throws SchedulerException {
    log.info("删除某个任务 name:" + name + " group:" + group);
    JobKey jobKey = new JobKey(name, group);
    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
    if (jobDetail == null) {
      return;
    }
    scheduler.deleteJob(jobKey);
  }

  /**
   * 添加一个任务
   */
  public void insertJob(String name, String group, String cron) throws SchedulerException {

    try {
      if (name.equals("job1")) {
        log.info("添加一个任务 name:" + name + " group:" + group + " cron:" + cron);
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(name, group).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(name, group)
            .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
      } else if (name.equals("job2")) {
        log.info("添加一个任务 name:" + name + " group:" + group + " cron:" + cron);
        JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity(name, group).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(name, group)
            .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
      } else {
        log.info("name名不合法");
      }
    }catch (RuntimeException e){
      log.info("Cron表达式不合法");
      return;
    }
  }

  private void startJob1() throws SchedulerException {
    // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
    // JobDetail 是具体Job实例
    JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
    // 基于表达式构建触发器
    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
    // CronTrigger表达式触发器 继承于Trigger
    // TriggerBuilder 用于构建触发器实例
    CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1", "group1")
        .withSchedule(cronScheduleBuilder).build();
    scheduler.scheduleJob(jobDetail, cronTrigger);
  }

}
