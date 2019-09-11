package com.atguigu.gmall0325.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0325.entity.UserAddress;
import com.atguigu.gmall0325.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("/getUserAddress")
    @ResponseBody
    public List<UserAddress> getUserAddress(String userId){

        return userInfoService.getUserAddressByUserId(userId);
    }
}
