package com.yonyou.einvoice.execute;

import com.yonyou.einvoice.entity.OptionEnum;
import com.yonyou.einvoice.timing.QuartzManager;
import com.yonyou.einvoice.util.JsonUtil;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 操作界面
 *
 * @author qiwen
 */
@Component
@Slf4j
public class Interface implements CommandLineRunner {

  @Autowired
  private DoWork doWork;

  @Autowired
  private Interface anInterface;

  @Autowired
  private JsonUtil jsonUtil;

  @Autowired
  private QuartzManager quartzManager;


  @Override
  public void run(String... args) throws Exception {
    Scanner scanner = new Scanner(System.in);
    System.out.println("输入数字1，进行一次全量");
    System.out.println("输入数字2，进行一次增量");
    System.out.println("输入数字3，进行定时增量");
    System.out.println("输入数字4，将Mysql表中数据转移至文件");
    System.out.println("输入数字5，将MongoDB表中数据转移至Mysql");
    System.out.println("输入数字6，退出");
    Integer option = scanner.nextInt();
    if (OptionEnum.ONE_FUll.getKey().equals(option)) {
      log.info("进行一次全量");
      doWork.doFull();
      anInterface.run();
    } else if (OptionEnum.ONE_INCREMENT.getKey().equals(option)) {
      log.info("进行一次增量");
      if (jsonUtil.getMaxTimeFile() == null) {
        log.info("目录中无最大ID记录文件，请先进行一次全量，或部署以max_ID为开头的文件，内容为最大ID");
      } else {
        doWork.doIncrement();
      }
      anInterface.run();
    } else if (OptionEnum.TIMING_INCREMENT.getKey().equals(option)) {
      log.info("进行定时增量");

      quartzManager.startJob();

    }else if (OptionEnum.MYSQL_FILE.getKey().equals(option)){
      log.info("将Mysql表中数据转移至文件中");
      doWork.doMysqltoFile();

      anInterface.run();
    } else if (OptionEnum.MONGODB_MYSQL.getKey().equals(option)) {
      log.info("将MongoDB表中数据转移至Mysql");
      doWork.doMongoDBtoMysql();

      anInterface.run();
    }else if (OptionEnum.EXIT.getKey().equals(option)) {
      log.info("退出");
      return;
    } else {
      log.info("非法输入,请重新输入");
      anInterface.run();
    }
  }
}
