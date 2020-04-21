package com.einvoice.controller;

import com.einvoice.dao.UserMapper;
import com.einvoice.entity.UserEntity;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired
  private UserMapper userMapper;
  //用户登陆
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(UserEntity user,HttpServletRequest request, HttpServletResponse response )throws Exception{
    if(user.getName()==null||user.getName()==""){
      response.sendRedirect(request.getContextPath() + "/login.html");
      return null;
    }
    UserEntity u = userMapper.login(user);
    if(u != null){
      request.getSession().setAttribute("USER",u);
      return "success";
    }else{
      return  "error";
    }
  }

  @RequestMapping(value = "/logout")
  public String logout(HttpSession session){
    session.invalidate();
    return "success";
  }

  //用户注册
  @RequestMapping("/addUser")
  public String addUser(UserEntity user,HttpServletRequest request,HttpServletResponse response )throws Exception{
    //ModelAndView mv =  new   ModelAndView();
    if(user.getName()==null||user.getName()==""){
      response.sendRedirect(request.getContextPath() + "/login.html");
      return null;
    }
    UserEntity u=userMapper.search(user);

    Integer flag=0;
    if(u==null) {
      flag = userMapper.addUser(user);
    }
    if(flag!=0){
      request.getSession().setAttribute("USER",user);
      //mv.setViewName("show");
      //  return mv;
      return "success";
    }else{
      //System.out.println("添加失败");
      //mv.setViewName("error");
      //  return mv;
      return  "error";
    }
  }
  //查找所有用户
      @RequestMapping("/findAllUser")
  public List<UserEntity> findAllUser(){
    List<UserEntity> users= userMapper.findAllUser();
    return users;
   }

   @RequestMapping("/deleteUserById")
  public String deleteUserById(String id){
    return  id;
   }

//    @RequestMapping("index")
//    public String index(Model model){
//        model.addAttribute("mgs","springboot-jsp");
//        return "index";
//    }

}
