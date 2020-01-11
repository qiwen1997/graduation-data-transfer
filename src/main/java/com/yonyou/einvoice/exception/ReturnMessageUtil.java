package com.yonyou.einvoice.exception;

import com.yonyou.einvoice.entity.ReturnMessage;

/**
 * json 消息处理工具类
 *
 * @author qiwen
 */
public class ReturnMessageUtil {

  /**
   * 无异常 请求成功并有具体内容返回
   */
  public static ReturnMessage<Object> sucess(Object object) {
    ReturnMessage<Object> message = new ReturnMessage<Object>(0, "sucess", object);
    return message;
  }

  /**
   * 无异常 请求成功并无具体内容返回
   */
  public static ReturnMessage<Object> sucess() {
    ReturnMessage<Object> message = new ReturnMessage<Object>(0, "sucess", null);
    return message;
  }

  /**
   * 有自定义错误异常信息
   */
  public static ReturnMessage<Object> error(Integer code, String msg) {
    ReturnMessage<Object> message = new ReturnMessage<Object>(code, msg, null);
    return message;
  }
}
