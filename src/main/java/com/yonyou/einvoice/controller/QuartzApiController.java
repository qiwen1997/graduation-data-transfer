package com.yonyou.einvoice.controller;

import com.yonyou.einvoice.exception.ReturnMessageUtil;
import com.yonyou.einvoice.execute.DoWork;
import com.yonyou.einvoice.timing.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 简单封装了一下api 提供定时任务的动态操作
 * @author qiwen
 */
@Slf4j
@RestController
public class QuartzApiController {

  @Autowired
  private QuartzManager quartzManager;

  @Autowired
  private DoWork doWork;

  @GetMapping("/start")
  public String startQuartzJob() {
    try {
      quartzManager.startJob();
    } catch (SchedulerException e) {
      log.error("start 开始任务出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping("/info")
  public String getQuartzJob(String name, String group) {
    String info = null;
    try {
      info = quartzManager.getJobInfo(name, group);
    } catch (SchedulerException e) {
      log.error("info 显示信息出现异常 {}", e.getMessage());
    }
    return info;
  }

  @GetMapping("/modify")
  public boolean modifyQuartzJob(String name, String group, String cron) {
    boolean flag = true;
    try {
      flag = quartzManager.modifyJob(name, group, cron);
    } catch (SchedulerException e) {
      log.error("modify 修改任务出现异常 {}", e.getMessage());
    }
    return flag;
  }

  @GetMapping("/insert")
  public String insertQuartzJob(String name, String group, String cron) {
    try {
      quartzManager.insertJob(name, group, cron);
    } catch (SchedulerException e) {
      log.error("insert 新增任务出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/pause")
  public String pauseQuartzJob(String name, String group) {
    try {
      quartzManager.pauseJob(name, group);
    } catch (SchedulerException e) {
      log.error("pause 暂停任务出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/pauseAll")
  public String pauseAllQuartzJob() {
    try {
      quartzManager.pauseAllJob();
    } catch (SchedulerException e) {
      log.error("pauseAll 全部暂停出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/resume")
  public String resumeQuartzJob(String name, String group) {
    try {
      quartzManager.resumeJob(name, group);
    } catch (SchedulerException e) {
      log.error("resume 恢复任务出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/resumeAll")
  public String resumeAllQuartzJob() {
    try {
      quartzManager.resumeAllJob();
    } catch (SchedulerException e) {
      log.error("resumeAll 恢复全部任务出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/delete")
  public String deleteJob(String name, String group) {
    try {
      quartzManager.deleteJob(name, group);
    } catch (SchedulerException e) {
      log.error("delete 删除任务出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/full")
  public String fullJob(String name, String group) {
    try {
      quartzManager.pauseAllJob();
      doWork.doFull();
      quartzManager.resumeAllJob();
    } catch (SchedulerException e) {
      log.error("full 全量任务出现异常 {}", e.getMessage());
    }
    return ReturnMessageUtil.sucess().toJSON();
  }
}

