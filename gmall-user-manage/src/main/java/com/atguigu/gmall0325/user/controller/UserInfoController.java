package com.atguigu.gmall0325.user.controller;

import com.atguigu.gmall0325.entity.UserInfo;
import com.atguigu.gmall0325.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/selectAll")
    @ResponseBody
    public List<UserInfo> selectAll(){
        return userInfoService.selectAll();
    }
}
