package com.yonyou.einvoice.execute;

import com.yonyou.einvoice.service.Datax;
import com.yonyou.einvoice.timing.QuartzManager;
import com.yonyou.einvoice.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DoWork {

  @Autowired
  private JsonUtil jsonUtil;

  @Autowired
  private Datax datax;

  @Autowired
  private QuartzManager quartzManager;

  @Value("${cron}")
  private String cron;

  /**
   * 进行一次全量
   */
  public void doFull(){
    datax.doMaxdateFile();
    //jsonUtil.setFull();
    datax.doFullFile();
  }

  /**
   * 进行一次增量
   */
  public void doIncrement(){
    String Olddate = jsonUtil.getMaxdate();
    datax.doMaxdateFile();
    String Newdate = jsonUtil.getMaxdate();
    jsonUtil.setMaxdate(Olddate, Newdate);
    datax.doIncrementFile();
  }

  public void doMysqltoFile(){
    datax.doMysqltoFile();
  }
}
