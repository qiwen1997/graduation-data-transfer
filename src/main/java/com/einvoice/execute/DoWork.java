package com.einvoice.execute;

import com.einvoice.service.Datax;
import com.einvoice.util.JsonUtil;
import com.einvoice.timing.QuartzManager;
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

  /**
   * 将Mysql数据转移到文件
   */
  public void doMysqltoFile(){
    datax.doMysqltoFile();
  }

  /**
   * 将MongoDB数据转移到Mysql
   */
  public void doMongoDBtoMysql(){
    datax.doMongoDBtoMysql();
  }
}
