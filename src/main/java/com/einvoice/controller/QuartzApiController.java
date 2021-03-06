package com.einvoice.controller;

import com.einvoice.execute.DoWork;
import com.einvoice.timing.QuartzManager;
import com.einvoice.util.JsonUtil;
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
  private JsonUtil jsonUtil;

  @Autowired
  private DoWork doWork;

  @GetMapping("/start")
  public void startQuartzJob() {
    try {
      quartzManager.startJob();
    } catch (SchedulerException e) {
      log.error("start 开始任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping("/info")
  public void getQuartzJob(String name, String group) {
    String info = null;
    try {
      info = quartzManager.getJobInfo(name, group);
    } catch (SchedulerException e) {
      log.error("info 显示信息出现异常 {}", e.getMessage());
    }
    log.info(info);
   // return info;
  }

  @GetMapping("/modify")
  public void modifyQuartzJob(String name, String group, String cron) {
    boolean flag = true;
    try {
      String ss=quartzManager.getJobInfo(name, group);
      if(ss.equals("没有查询到 Job的name："+name+" group:" + group+" 的任务的信息")){
        log.info(ss);
        return;
      }
      flag = quartzManager.modifyJob(name, group, cron);
    } catch (SchedulerException e) {
      log.error("modify 修改任务出现异常 {}", e.getMessage());
    }

    //return flag;
  }

  @GetMapping("/insert")
  public void insertQuartzJob(String name, String group, String cron) {
    try {
      name=name.trim();
      group=group.trim();
      cron=cron.trim();
      String ss=quartzManager.getJobInfo(name, group);
      if(!ss.equals("没有查询到 Job的name："+name+" group:" + group+" 的任务的信息")){
        log.info("任务已存在，添加任务失败");
        return;
      }
      quartzManager.insertJob(name, group, cron);
    } catch (SchedulerException e) {
      log.error("insert 新增任务出现异常 {}", e.getMessage());
    }
   // return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/pause")
  public void pauseQuartzJob(String name, String group) {
    try {
      String ss=quartzManager.getJobInfo(name, group);
      if(ss.equals("没有查询到 Job的name："+name+" group:" + group+" 的任务的信息")){
        log.info(ss);
        return;
      }
      quartzManager.pauseJob(name, group);
    } catch (SchedulerException e) {
      log.error("pause 暂停任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/pauseAll")
  public void pauseAllQuartzJob() {
    try {
      quartzManager.pauseAllJob();
    } catch (SchedulerException e) {
      log.error("pauseAll 全部暂停出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/resume")
  public void resumeQuartzJob(String name, String group) {
    try {
      String ss=quartzManager.getJobInfo(name, group);
      if(ss.equals("没有查询到 Job的name："+name+" group:" + group+" 的任务的信息")){
        log.info(ss);
        return;
      }
      quartzManager.resumeJob(name, group);
    } catch (SchedulerException e) {
      log.error("resume 恢复任务出现异常 {}", e.getMessage());
    }
   // return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/resumeAll")
  public void resumeAllQuartzJob() {
    try {
      quartzManager.resumeAllJob();
    } catch (SchedulerException e) {
      log.error("resumeAll 恢复全部任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/delete")
  public void deleteJob(String name, String group) {
    try {
      String ss=quartzManager.getJobInfo(name, group);
      if(ss.equals("没有查询到 Job的name："+name+" group:" + group+" 的任务的信息")){
        log.info(ss);
        return;
      }
      quartzManager.deleteJob(name, group);
    } catch (SchedulerException e) {
      log.error("delete 删除任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/oneFull")
  public void oneFull() {
    try {
      quartzManager.pauseAllJob();
      log.info("进行一次全量");
      doWork.doFull();
      quartzManager.resumeAllJob();
    } catch (SchedulerException e) {
      log.error("oneFull 一次全量任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/oneIncrement")
  public void oneIncrement() {
    try {
      quartzManager.pauseAllJob();
      log.info("进行一次增量");
      if (jsonUtil.getMaxTimeFile() == null) {
        log.info("目录中无最大ID记录文件，请先进行一次全量，或部署以max_ID为开头的文件，内容为最大ID");
      } else {
        doWork.doIncrement();
      }
      quartzManager.resumeAllJob();
    } catch (SchedulerException e) {
      log.error("oneIncrement 一次增量任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }


  @GetMapping(value = "/MysqltoFile")
  public void MysqltoFile() {
    try {
      quartzManager.pauseAllJob();
      log.info("将Mysql表中数据转移至文件中");
      doWork.doMysqltoFile();
      quartzManager.resumeAllJob();
    } catch (SchedulerException e) {
      log.error("oneFull 一次全量任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

  @GetMapping(value = "/MongoDBtoMysql")
  public void MongoDBtoMysql() {
    try {
      quartzManager.pauseAllJob();
      log.info("将MongoDB表中数据转移至Mysql");
      doWork.doMongoDBtoMysql();
      quartzManager.resumeAllJob();
    } catch (SchedulerException e) {
      log.error("oneFull 一次全量任务出现异常 {}", e.getMessage());
    }
    //return ReturnMessageUtil.sucess().toJSON();
  }

}

