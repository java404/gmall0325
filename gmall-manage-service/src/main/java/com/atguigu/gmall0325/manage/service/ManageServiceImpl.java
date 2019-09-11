package com.atguigu.gmall0325.manage.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall0325.config.RedisUtil;
import com.atguigu.gmall0325.entity.*;
import com.atguigu.gmall0325.manage.constant.ManageConst;
import com.atguigu.gmall0325.manage.mapper.*;
import com.atguigu.gmall0325.service.ManageService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<BaseCatalog1> getCatalog1() {

/*        try {
            Jedis jedis = redisUtil.getJedis();
            jedis.set("k1","v1");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {
/*        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return baseAttrInfoMapper.select(baseAttrInfo);*/

        return baseAttrInfoMapper.getAttrList(catalog3Id);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {

        //如果有主键就进行更新,如果没有就插入
        if(baseAttrInfo.getId()!=null&&baseAttrInfo.getId().length()>0){
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else{
            baseAttrInfo.setId(null);
            //插入info信息
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        //把info里面的attr
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);


        if(baseAttrInfo.getAttrValueList()!=null&&baseAttrInfo.getAttrValueList().size()>0){
            for (BaseAttrValue attrValue : baseAttrInfo.getAttrValueList()) {
                attrValue.setId(null);
                attrValue.setAttrId((baseAttrInfo.getId()));
                baseAttrValueMapper.insertSelective(attrValue);
            }
        }
    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        //创建属性对象
        BaseAttrInfo attrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        //创建属性值对象
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        //根据attrId字段查询对象
        baseAttrValue.setAttrId(attrInfo.getId());
        List<BaseAttrValue> attrValueList = baseAttrValueMapper.select(baseAttrValue);
        //给属性对象中的属性值集合赋值

        attrInfo.setAttrValueList(attrValueList);
        return attrInfo;
    }

    @Override
    public List<SpuInfo> spuList(String catalog3Id) {
        //select * from spuinfo where catalog3id = ?
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {

        return baseSaleAttrMapper.selectAll();
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //spuinfo
        //spuimage
        //spusaleattr
        //spusaleattrvalue

        spuInfoMapper.insertSelective(spuInfo);

        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if(spuImageList!=null && spuImageList.size()>0){
            for (SpuImage spuImage : spuImageList) {
                spuImage.setId(null);
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }

        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList!=null && spuSaleAttrList.size()>0){
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                spuSaleAttr.setId(null);
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);

                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if(spuSaleAttrValueList!=null && spuSaleAttrValueList.size()>0){
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setId(null);
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }
            }
        }
    }

    @Override
    public List<SpuImage> spuImageList(String spuId) {

        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(String spuId) {

        return spuSaleAttrMapper.spuSaleAttrList(spuId);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        //skuInfo
        //skuImage
        //skuAttrValue
        //skuSaleAttrValue
        skuInfoMapper.insertSelective(skuInfo);

        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if(skuImageList!=null && skuImageList.size()>0){
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insertSelective(skuImage);
            }
        }

        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if(skuAttrValueList!=null && skuAttrValueList.size()>0){
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insertSelective(skuAttrValue);
            }
        }

        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if(skuSaleAttrValueList!=null && skuSaleAttrValueList.size()>0){
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        }
    }

    @Override
    public SkuInfo getSkuInfoById(String skuId) {

        SkuInfo skuInfo = null;
        Jedis jedis = null;
        RLock lock = null;
        String skuKey = ManageConst.SKUKEY_PREFIX + skuId + ManageConst.SKUKEY_SUFFIX;
        try {
            jedis = redisUtil.getJedis();

            String skuInfoJson = jedis.get(skuKey);
            //如果缓存中有直接从缓存命中否则走数据库
            if(skuInfoJson!=null){
                skuInfo = JSON.parseObject(skuInfoJson, SkuInfo.class);
                System.out.println("走缓存");
                return skuInfo;
            }else{
                System.out.println("在缓存中没有命中");
                //加锁
                Config config = new Config();
                config.useSingleServer().setAddress("redis://192.168.157.120:6379");

                //创建redisson instance
                RedissonClient redisson = Redisson.create(config);

                //获取锁
                lock = redisson.getLock("myLock");
                lock.lock(10, TimeUnit.SECONDS);

                skuInfo = getSkuInfoDB(skuId);
                skuInfoJson = JSON.toJSONString(skuInfo);
                System.out.println("redis活着呢");
                //把skuInfo存到redis缓存中
                jedis.setex(skuKey,ManageConst.SKUKEY_TIMEOUT, skuInfoJson);

                return skuInfo;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis!=null){
                jedis.close();
            }
            if(lock!=null){
                lock.unlock();
            }

        }
        return getSkuInfoDB(skuId);
        //return getSkuInfoLock(skuId);
    }

    private SkuInfo getSkuInfoLock(String skuId) {
        SkuInfo skuInfo = null;
        Jedis jedis = null;
        String skuKey = ManageConst.SKUKEY_PREFIX + skuId + ManageConst.SKUKEY_SUFFIX;
        try {
            jedis = redisUtil.getJedis();

            String skuInfoJson = jedis.get(skuKey);
            //如果缓存中有直接从缓存命中否则走数据库
            if(skuInfoJson!=null){
                skuInfo = JSON.parseObject(skuInfoJson, SkuInfo.class);
                System.out.println("走缓存");
                return skuInfo;
            }else{
                System.out.println("在缓存中没有命中");
                //定义锁key
                String skuLockKey = ManageConst.SKUKEY_PREFIX + skuId + ManageConst.SKULOCK_SUFFIX;
                //生成锁
                String lockKey = jedis.set(skuLockKey, "OK", "NX", "PX", ManageConst.SKULOCK_EXPIRE_PY);
                if ("OK".equals(lockKey)){
                    skuInfo = getSkuInfoDB(skuId);
                    skuInfoJson = JSON.toJSONString(skuInfo);
                    System.out.println("redis活着呢");
                    //把skuInfo存到redis缓存中
                    jedis.setex(skuKey,ManageConst.SKUKEY_TIMEOUT, skuInfoJson);
                }else{
                    System.out.println("等待");
                    Thread.sleep(1000);
                    return getSkuInfoById(skuKey);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis!=null){
                jedis.close();
            }

        }
        return getSkuInfoDB(skuId);
    }

    private SkuInfo getSkuInfoDB(String skuId) {

        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImages = skuImageMapper.select(skuImage);
        skuInfo.setSkuImageList(skuImages);

        System.out.println("走数据库");
        return skuInfo;
    }

    @Override
    public List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(SkuInfo skuInfo) {
        return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(skuInfo.getId(),skuInfo.getSpuId());
    }

    @Override
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId) {
        return skuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);

    }
}
