package com.yonyou.einvoice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class JspController {

    @RequestMapping("/loginjsp")
    public ModelAndView indexx(){
        ModelAndView modelAndView = new ModelAndView("/loginjsp");
        return modelAndView;
    }

    @RequestMapping("/cat")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/hh"); //设置对应JSP的模板文件
        modelAndView.addObject("hi", "Hello,Cat"); //设置${hi}标签的值为Hello,Cat
        return modelAndView;
    }
}

