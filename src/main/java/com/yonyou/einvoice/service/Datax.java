package com.yonyou.einvoice.service;

import java.io.File;

public interface Datax {

//  File[] getFileList();

  /**
   * 查找最大date，写入文件
   */
  void doMaxdateFile();

  /**
   * 进行全量
   */
  void doFullFile();

  /**
   * 进行增量
   */
  void doIncrementFile();

  /**
   * 将Mysql数据转移到文件
   */
  void doMysqltoFile();
}
