package com.controller;

import com.domain.User;
import com.domain.VO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller()
public class HelloController {

    /**
     * 获取数据形式一：直接利用形参获取数据
     * @return
     */
    @RequestMapping(path = "/hello")
    @ResponseBody
    public void sayHello(String name, int age){
        System.out.println(name);
        System.out.println(age);
    }

    /**
     * 获取数据形式二：获取POJO类型参数
     * @return
     */
    @RequestMapping(path = "/hello2")
    @ResponseBody
    public void sayHello(User user){
        System.out.println(user);
    }

    /**
     * 获取数据形式三：获取数组类型参数
     * @return
     */
    @RequestMapping(path = "/hello3")
    @ResponseBody
    public void sayHello(String[] str){
        System.out.println(Arrays.asList(str));
    }

    /**
     * 获取数据形式四：获取集合类型参数(1)
     * 包装至一个POJO对象中，对应属性名称一致即可
     * @return
     */
    @RequestMapping(path = "/hello4")
    @ResponseBody
    public void sayHello4(VO vo){
        System.out.println(vo);
    }

    /**
     * 获取数据形式五：获取集合类型参数(2)
     * 方法的形参处，利用@RequestBody直接获取集合类型的信息，无需POJO对象封装
     * @return
     */
    @RequestMapping(path = "/hello5")
    @ResponseBody
    public void sayHello5(@RequestBody List<User> userList){
        System.out.println(userList);
    }

    /**
     * 六、请求参数绑定
     * RequestParam注解绑定参数
     * 如下，即将username和name绑定，使得前端传来的数据名称即便是username，也能够成功识别
     * @return
     */
    @RequestMapping(path = "/hello6")
    @ResponseBody
    public void sayHello6(@RequestParam(value = "username", required = false, defaultValue = "lijuce") String name){
        System.out.println(name);
    }

    /**
     * 七、获取Restful风格的参数
     * Restful风格请求：**"url+请求方式"**表示一次请求目的，利用占位符及配合相应注解解析
     * PathVariable注解使用
     * 例如:http://localhost:8080/hello7/lijuce
     * @return
     */
    @RequestMapping(path = "/hello7/{username}")
    @ResponseBody
    public void sayHello7(@PathVariable(value = "username", required = true) String name){
        System.out.println(name);
    }

    /**
     * 八、自定义类型转换器
     * 此处以自定义日期转换器类为例
     * 例如:http://localhost:8080/hello7/lijuce
     * @return
     */
    @RequestMapping(path = "/hello8/{date}")
    @ResponseBody
    public void sayHello8(@PathVariable(value = "date") Date date){
        System.out.println(date);
    }

    /**
     * 九、获取Servlet相关API（直接作为方法参数传入
     * @return
     */
    @RequestMapping(path = "/hello8")
    @ResponseBody
    public void sayHello8(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
        System.out.println(request);
        System.out.println(response);
        System.out.println(httpSession);
    }


}
