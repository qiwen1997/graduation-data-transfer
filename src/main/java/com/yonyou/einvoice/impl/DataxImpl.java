package com.yonyou.einvoice.impl;

import com.yonyou.einvoice.service.Datax;
import com.yonyou.einvoice.util.JsonUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * datax相关操作
 *
 * @author qiwen
 */
@Service
public class DataxImpl implements Datax {

  private Logger logger = LoggerFactory.getLogger(DataxImpl.class);

  @Autowired
  private JsonUtil jsonUtil;

  @Value("${dataxPath}")
  private String dataxpath;

  @Value("${fullJsonPath}")
  private String fullJsonPath;

  @Value("${incrementJsonPath}")
  private String incrementJsonPath;

  @Value("${fileJsonPath}")
  private String fileJsonPath;

  @Value("${pythonPath}")
  private String pythonPath;

  /**
   * 获得最大date，写入文件
   */
  @Override
  public void doMaxdateFile() {
    File f = new File(fileJsonPath);
    String cmd = pythonPath + " " + dataxpath + " " + f.getAbsolutePath();
    try {
      Process process = Runtime.getRuntime().exec(cmd);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(process.getInputStream(), "UTF-8"));
      String line = null;
      while ((line = in.readLine()) != null) {
        logger.info(line);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 进行全量
   */
  @Override
  public void doFullFile() {
    File f = new File(fullJsonPath);
    String cmd = pythonPath + " " + dataxpath + " " + f.getAbsolutePath();
    try {
      Process process = Runtime.getRuntime().exec(cmd);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(process.getInputStream(), "UTF-8"));
      String line = null;
      while ((line = in.readLine()) != null) {
        logger.info(line);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 进行增量
   */
  @Override
  public void doIncrementFile() {
    File f = new File(incrementJsonPath);
    String cmd = pythonPath + " " + dataxpath + " " + f.getAbsolutePath();
    try {
      Process process = Runtime.getRuntime().exec(cmd);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(process.getInputStream(), "UTF-8"));
      String line = null;
      while ((line = in.readLine()) != null) {
        logger.info(line);
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
