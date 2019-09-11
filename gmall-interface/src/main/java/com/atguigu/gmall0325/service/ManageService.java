package com.atguigu.gmall0325.service;

import com.atguigu.gmall0325.entity.*;

import java.util.List;

public interface ManageService {

    /**
     * 获取所有的一级标题
     * @return
     */
    List<BaseCatalog1> getCatalog1();

    /**
     * 根据标题1的ID获取标题2列表
     * @param catalog1Id
     * @return
     */
    List<BaseCatalog2> getCatalog2(String catalog1Id);

    /**
     * 根据标题2的ID获取标题3列表
     * @param catalog2Id
     * @return
     */
    List<BaseCatalog3> getCatalog3(String catalog2Id);

    /**
     * 根据标题3的id获取属性名称
     * @param catalog3Id
     * @return
     */
    List<BaseAttrInfo> getAttrList(String catalog3Id);


    /**
     * 添加属性值
     * @param baseAttrInfo
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 根据平台属性的id查询平台属性值用于页面的回显
     * @param attrId
     * @return
     */
    BaseAttrInfo getAttrInfo(String attrId);


    /**
     * 根据三级类别id查找商品的信息列表
     * @param catalog3Id
     * @return
     */
    List<SpuInfo> spuList(String catalog3Id);

    /**
     * 获取商品的销售属性列表
     * @return
     */
    List<BaseSaleAttr> baseSaleAttrList();

    /**
     * 保存商品属性信息
     * @param spuInfo
     */
    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 根据spuid获取图片资源信息
     * @param spuId
     * @return
     */
    List<SpuImage> spuImageList(String spuId);

    /**
     * 根据spuid获取商品销售属性
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> spuSaleAttrList(String spuId);

    /**
     * 保存skuinfo信息
     * @param skuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo);

    /**
     * 根据skuId查询skuinfo的基本信息
     * @param skuId
     * @return
     */
    SkuInfo getSkuInfoById(String skuId);

    /**
     * 根据skuId和spuId到
     * SpuSaleAttr和SpuSaleAttrValue
     * 查询出销售属性以及销售属性值
     * @param skuInfo
     * @return
     */
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(SkuInfo skuInfo);

    /**
     * 根据spuId查询出所有销售属性组合
     * @param spuId
     * @return
     */
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId);
}
