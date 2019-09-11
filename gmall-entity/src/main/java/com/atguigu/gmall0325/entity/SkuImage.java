package com.atguigu.gmall0325.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SkuImage implements Serializable {

    private static final long serialVersioniUID = 1;

    @Id
    @Column
    String id;
    @Column
    String skuId;
    @Column
    String imgName;
    @Column
    String imgUrl;
    @Column
    String spuImgId;
    @Column
    String isDefault;
}
