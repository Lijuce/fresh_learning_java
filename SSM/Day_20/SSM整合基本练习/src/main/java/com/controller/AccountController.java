package com.controller;

import com.domain.Account;
import com.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/account")  // 请求的一级目录
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @RequestMapping("/findAll")  // 请求的二级目录
    public String findAllAcount(Model model){
        System.out.println("查找所有用户信息");
        List<Account> all = accountService.findAll();
        model.addAttribute("list", all);
        return "list";  // 跳转页面
    }
}
