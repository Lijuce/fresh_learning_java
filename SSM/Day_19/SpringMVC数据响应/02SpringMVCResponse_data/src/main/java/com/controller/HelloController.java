package com.controller;

import com.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller()
public class HelloController {

    /**
     * 回写数据形式一：利用注解@ResponseBody，直接返回字符串作为数据回写
     * @return
     */
    @RequestMapping(path = "/hello")
    @ResponseBody
    public String sayHello(){
        return "hello Lijuce!";
    }

    /**
     * 回写数据返回对象和集合形式一：利用SpringMVC内置的处理器适配器RequestMappingHandlerAdapter
     * 于xml文件中进行配置自动注入，可返回json格式的数据
     * @return
     */
    @RequestMapping(path = "/hello2")
    @ResponseBody
    public User sayHello2(){
        User user = new User();
        user.setName("lijce");
        user.setAge(25);
        return user;
    }

}
