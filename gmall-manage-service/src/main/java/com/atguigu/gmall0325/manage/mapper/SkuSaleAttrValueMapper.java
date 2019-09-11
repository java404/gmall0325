package com.atguigu.gmall0325.manage.mapper;

import com.atguigu.gmall0325.entity.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {

    public List<SkuSaleAttrValue> selectSkuSaleAttrValueListBySpu(String spuId);
}
