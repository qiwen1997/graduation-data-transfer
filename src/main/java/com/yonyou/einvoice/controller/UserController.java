package com.yonyou.einvoice.controller;

import com.yonyou.einvoice.dao.UserMapper;
import com.yonyou.einvoice.entity.UserEntity;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
  @Autowired
  private UserMapper userMapper;
  //用户登陆
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(@RequestParam("name") String name,@RequestParam("password") String password
     /* UserEntity user*/,HttpServletRequest request ){
    UserEntity user=new UserEntity();
    user.setName(name);
    user.setPassword(password);
    UserEntity u = userMapper.login(user);
    if(u != null){
      request.getSession().setAttribute("USER",u);
      return "success";
    }else{
      return  "error";
    }
  }
  //用户注册
  @RequestMapping("/addUser")
  public String addUser(UserEntity user){
    ModelAndView mv =  new   ModelAndView();
    boolean flag = userMapper.addUser(user);
    if(flag){
      System.out.println("已添加");
      mv.setViewName("show");
      //  return mv;
      return "success";
    }else{
      System.out.println("添加失败");
      mv.setViewName("error");
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
