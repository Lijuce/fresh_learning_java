package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller("helloController")
public class HelloController {
    /**
     * 接收请求
     * @return
     */
    @RequestMapping(path = "/hello")
    public ModelAndView sayHello(){
        // ModelAndView
        // 作用：封装和展示数据
        ModelAndView modelAndView = new ModelAndView();
        // 设置模型数据
        modelAndView.addObject("username", "Lijuce");
        // 设置视图名称
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * ModelandView使用形式二-SpringMVC自动注入modelAndView
     * @param modelAndView
     * @return
     */
    @RequestMapping(path = "/hello2")
    public ModelAndView sayHello(ModelAndView modelAndView){
        modelAndView.addObject("username", "Lijuce");
        // 设置视图名称
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     * ModelandView使用形式三-model和view分开使用
     * @param model
     * @return
     */
    @RequestMapping(path = "/hello3")
    public String sayHello(Model model){
        model.addAttribute("username", "Lijuce");
        return "success";
    }

}
