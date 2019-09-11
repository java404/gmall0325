package com.atguigu.gmall0325.manage.mapper;

import com.atguigu.gmall0325.entity.BaseAttrInfo;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {

    List<BaseAttrInfo> getAttrList(String catalog3Id);
}
