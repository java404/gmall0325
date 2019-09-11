package com.atguigu.gmall0325.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0325.entity.SkuInfo;
import com.atguigu.gmall0325.entity.SpuImage;
import com.atguigu.gmall0325.entity.SpuSaleAttr;
import com.atguigu.gmall0325.service.ManageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class SkuManageController {

    @Reference
    private ManageService manageService;


    //http://localhost:8082/spuImageList?spuId=40
    @RequestMapping("spuImageList")
    public List<SpuImage> spuImageList(String spuId){

        return manageService.spuImageList(spuId);
    }

    //http://localhost:8082/spuSaleAttrList?spuId=59
    @RequestMapping("spuSaleAttrList")
    public List<SpuSaleAttr> spuSaleAttrList(String spuId){

        return manageService.spuSaleAttrList(spuId);
    }

    //http://localhost:8082/saveSkuInfo
    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
    }
}
