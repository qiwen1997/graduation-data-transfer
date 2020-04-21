package com.einvoice.config;

import com.einvoice.entity.UserEntity;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    try {
      //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
      UserEntity user=(UserEntity)request.getSession().getAttribute("USER");
      if(user!=null){
        log.info(user.getName()+"登录");
        return true;
      }else {
        response.sendRedirect(request.getContextPath() + "/login.html");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
    //如果设置为true时，请求将会继续执行后面的操作
  }

//  @Override
//  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//      ModelAndView modelAndView) throws Exception {
////
////    try {
////      //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
////      UserEntity user=(UserEntity)request.getSession().getAttribute("USER");
////      if(user!=null){
////        //log.info(user.getName()+"登录");
////        //return true;
////      }else {
////        response.sendRedirect(request.getContextPath() + "/login.html");
////      }
////    } catch (IOException e) {
////      e.printStackTrace();
////    }
////   // return false;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
////    //如果设置为true时，请求将会继续执行后面的操作
//  }

//  @Override
//  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
//      Object handler, Exception ex) throws Exception {
////
////    try {
////      //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
////      UserEntity user=(UserEntity)request.getSession().getAttribute("USER");
////      if(user!=null){
////        //log.info(user.getName()+"登录");
////        //return true;
////      }else {
////        response.sendRedirect(request.getContextPath() + "/login.html");
////      }
////    } catch (IOException e) {
////      e.printStackTrace();
////    }
////    // return false;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
////    //如果设置为true时，请求将会继续执行后面的操作
//  }
}
