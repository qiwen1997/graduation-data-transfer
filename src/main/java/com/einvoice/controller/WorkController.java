package com.einvoice.controller;

import com.einvoice.execute.DoWork;
import com.einvoice.timing.QuartzManager;
import com.einvoice.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WorkController {

  @Autowired
  private DoWork doWork;

  @Autowired
  private JsonUtil jsonUtil;

  @Autowired
  private QuartzManager quartzManager;

  @RequestMapping("/doOneFull")
  public void doOneFull(){
    log.info("进行一次全量");
    doWork.doFull();
  }

  @RequestMapping("/doOneIncrement")
  public void doOneIncrement(){
    log.info("进行一次增量");
    if (jsonUtil.getMaxTimeFile() == null) {
      log.info("目录中无最大ID记录文件，请先进行一次全量，或部署以max_ID为开头的文件，内容为最大ID");
    } else {
      doWork.doIncrement();
    }
  }

  @RequestMapping("/doTimingIncrement")
  public void doTimingIncrement() throws  Exception{
    log.info("进行定时增量");

    quartzManager.startJob();
  }

  @RequestMapping("/doMysqltoFile")
  public void doMysqltoFile(){
    log.info("将Mysql表中数据转移至文件中");
    doWork.doMysqltoFile();
  }

  @RequestMapping("/doMongoDBtoMysql")
   public void doMongoDBtoMysql(){
    log.info("将MongoDB表中数据转移至Mysql");
    doWork.doMongoDBtoMysql();
  }


}
