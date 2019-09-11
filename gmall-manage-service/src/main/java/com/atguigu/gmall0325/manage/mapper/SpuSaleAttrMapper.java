package com.atguigu.gmall0325.manage.mapper;

import com.atguigu.gmall0325.entity.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {

    List<SpuSaleAttr> spuSaleAttrList(String spuId);

    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(String skuId, String spuId);
}
