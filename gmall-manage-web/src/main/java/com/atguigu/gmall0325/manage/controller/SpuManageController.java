package com.atguigu.gmall0325.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0325.entity.BaseSaleAttr;
import com.atguigu.gmall0325.entity.SpuInfo;
import com.atguigu.gmall0325.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class SpuManageController {

    @Reference
    private ManageService manageService;


    @RequestMapping("spuList")
    @ResponseBody
    public List<SpuInfo> spuList(String catalog3Id){

        return manageService.spuList(catalog3Id);

    }


    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> baseSaleAttrList(){
        return manageService.baseSaleAttrList();
    }


    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public void saveSpuInfo(@RequestBody SpuInfo spuInfo){

        manageService.saveSpuInfo(spuInfo);
    }

}
