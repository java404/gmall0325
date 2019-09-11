package com.atguigu.gmall0325.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall0325.entity.SkuInfo;
import com.atguigu.gmall0325.entity.SkuSaleAttrValue;
import com.atguigu.gmall0325.entity.SpuSaleAttr;
import com.atguigu.gmall0325.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class ItemController {

    @Reference
    private ManageService manageService;

    @RequestMapping("{skuId}.html")
    public String skuInfoPage(@PathVariable(value = "skuId") String skuId, Model model){

        //获取商品销售基本信息
        SkuInfo skuInfo = manageService.getSkuInfoById(skuId);
        model.addAttribute("skuInfo", skuInfo);
        System.out.println("skuId:" + skuInfo.getId());

        //获取商品销售属性及商品销售属性值
        List<SpuSaleAttr> spuSaleAttrList = manageService.selectSpuSaleAttrListCheckBySku(skuInfo);
        model.addAttribute("spuSaleAttrList",spuSaleAttrList);
        System.out.println(spuSaleAttrList);

        //拼接json字符串传至前台
        //{"46|50":"10","47|50":"13","48|49":"12"}
        List<SkuSaleAttrValue> skuSaleAttrValueListBySpu = manageService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());

        String valueIdsKey = "";

        HashMap<String, String> valuesSkuMap = new HashMap<>();

        for (int i = 0; i < skuSaleAttrValueListBySpu.size(); i++) {
            SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueListBySpu.get(i);

            if(valueIdsKey.length()!=0){
                valueIdsKey = valueIdsKey + "|";
            }

            valueIdsKey = valueIdsKey+skuSaleAttrValue.getSaleAttrValueId();

            if((i+1)==skuSaleAttrValueListBySpu.size()||!skuSaleAttrValue.getSkuId().equals(skuSaleAttrValueListBySpu.get(i+1).getSkuId())){
                valuesSkuMap.put(valueIdsKey,skuSaleAttrValue.getSkuId());
                valueIdsKey="";
            }

        }

        //把map变成json串
        String valuesSkuJson = JSON.toJSONString(valuesSkuMap);
        model.addAttribute("valuesSkuJson", valuesSkuJson);
        System.out.println("valuesSkuJson:" + valuesSkuJson);

        return "item";
    }
}
