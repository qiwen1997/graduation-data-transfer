package com.yonyou.einvoice.exception;

import com.yonyou.einvoice.entity.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一处理异常
 *
 * @author qiwen
 */
@RestControllerAdvice
public class ExceptionHandle {

  private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

  @ExceptionHandler(value = Exception.class)
  public ReturnMessage<Object> handle(Exception exception) {
    if (exception instanceof DefinedException) {
      DefinedException sbexception = (DefinedException) exception;
      return ReturnMessageUtil.error(sbexception.getCode(), sbexception.getMessage());
    } else {
      logger.error("系统异常 {}", exception);
      return ReturnMessageUtil.error(-1, "未知异常" + exception.getMessage());
    }
  }
}
