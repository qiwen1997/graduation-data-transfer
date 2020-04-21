package com.einvoice.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JSON文件操作类
 *
 * @author qiwen
 */
@Slf4j
@Component
public class JsonUtil {

  @Value("${maxTimeFilePath}")
  private String maxTimeFilePath;

  @Value("${incrementJsonPath}")
  private String incrementJsonPath;

  /**
   * 找到记录最大时间的文件
   */
  public File getMaxTimeFile() {
    File file = new File(maxTimeFilePath);
    File[] files = file.listFiles();
    for (int i = 0; i < files.length; i++) {
      if (files[i].getName().startsWith("max_date")) {
        return files[i];
      }
    }
    return null;
  }

  /**
   * 获得文件中的最大date
   */
  public String getMaxdate() {
    File file = getMaxTimeFile();
    String date = null;
    if (file == null) {
      log.info("没有找到max_date文件，返回数字0");
      return "0";
    } else {
      Path path = file.toPath();
      try {
        BufferedReader bufferedReader = Files.newBufferedReader(path);
        date = bufferedReader.readLine();
        bufferedReader.close();
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }
    return date;
  }

  /**
   * 把增量文件中的where改为>date
   */
  public void setMaxdate(String Olddate, String Newdate) {
    Path path = Paths.get(incrementJsonPath);
    StringBuffer stringBuffer = new StringBuffer();
    try {
      BufferedReader bufferedReader = Files.newBufferedReader(path);
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line);
      }
      bufferedReader.close();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
    JSONObject job = jsonObject.getJSONObject("job");
    JSONArray content = job.getJSONArray("content");
    JSONObject reader = content.getJSONObject(0).getJSONObject("reader");
    JSONObject parameter = reader.getJSONObject("parameter");
    parameter.put("where", "date>" + "'"+Olddate+"'" + " and date<=" +"'"+ Newdate+"'");

    reader.put("parameter", parameter);
    content.getJSONObject(0).put("reader", reader);
    job.put("content", content);
    jsonObject.put("job", job);
    //System.out.println(jsonObject.toString());
    try {
      Files.write(path, jsonObject.toJSONString().getBytes());
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * 把增量文件中的where改为1=1 使其变为全量
   */
  public void setFull() {
    Path path = Paths.get(incrementJsonPath);
    StringBuffer stringBuffer = new StringBuffer();
    try {
      BufferedReader bufferedReader = Files.newBufferedReader(path);
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuffer.append(line);
      }
      bufferedReader.close();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
    JSONObject job = jsonObject.getJSONObject("job");
    JSONArray content = job.getJSONArray("content");
    JSONObject reader = content.getJSONObject(0).getJSONObject("reader");
    JSONObject parameter = reader.getJSONObject("parameter");
    parameter.put("where", "1=1");

    reader.put("parameter", parameter);
    content.getJSONObject(0).put("reader", reader);
    job.put("content", content);
    jsonObject.put("job", job);
    //System.out.println(jsonObject.toString());
    try {
      Files.write(path, jsonObject.toJSONString().getBytes());
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
}
