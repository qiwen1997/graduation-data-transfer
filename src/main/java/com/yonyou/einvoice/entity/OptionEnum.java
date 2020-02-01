package com.yonyou.einvoice.entity;

/**
 * 操作选项枚举类 1一次全量 2一次增量 3定时增量 4退出
 *
 * @author qiwen
 */
public enum OptionEnum {
  ONE_FUll(1, "OneFull"),
  ONE_INCREMENT(2, "OneIncrement"),
  TIMING_INCREMENT(3, "TimingIncrement"),
  MYSQL_FILE(4, "MysqltoFile"),
  MONGODB_MYSQL(5, "MongoDBtoMysql"),
  EXIT(6, "Exit");

  private Integer key;
  private String msg;

  OptionEnum(Integer key, String msg) {
    this.key = key;
    this.msg = msg;
  }

  public Integer getKey() {
    return key;
  }

  public String getMsg() {
    return msg;
  }
}
