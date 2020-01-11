package com.yonyou.einvoice.timing;


import com.yonyou.einvoice.execute.DoWork;
import com.yonyou.einvoice.service.Datax;
import com.yonyou.einvoice.util.JsonUtil;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定时任务具体任务
 *
 * @author qiwen
 */
@Slf4j
public class MyJob implements Job {

  @Autowired
  private DoWork doWork;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.info("任务开始：" + LocalDateTime.now() + " name:" + context.getJobDetail().getKey().getName() +
        " group:" + context.getJobDetail().getKey().getGroup());
    doWork.doIncrement();
    log.info("任务结束：" + LocalDateTime.now());
  }
}
