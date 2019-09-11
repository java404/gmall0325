package com.atguigu.gmall0325.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall0325.entity.UserAddress;
import com.atguigu.gmall0325.user.mapper.UserAddressMapper;
import com.atguigu.gmall0325.user.mapper.UserInfoMapper;
import com.atguigu.gmall0325.entity.UserInfo;
import com.atguigu.gmall0325.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserInfo> selectAll() {
        return userInfoMapper.selectAll();
    }

    @Override
    public UserInfo selectUserById(String id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserInfo> selectUserByName(String name) {

        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("name", name);
        return userInfoMapper.selectByExample(example);
    }

    @Override
    public List<UserInfo> selectUserByAll(UserInfo userInfo) {

        return userInfoMapper.select(userInfo);
    }

    @Override
    public List<UserInfo> selectUserLikeName(String name) {

        Example example = new Example(UserInfo.class);
        example.createCriteria().andLike("name", "%"+name+"%");
        return userInfoMapper.selectByExample(example);
    }

    @Override
    public void insertUser(UserInfo userInfo) {

        userInfo.setPhoneNum("123456");
        userInfo.setEmail("115@qq.com");
        userInfo.setNickName("ggsimida");
        userInfoMapper.insertSelective(userInfo);

        System.out.println(userInfo.getId());
    }

    @Override
    public void updateUserById(UserInfo userInfo) {

        userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public void deleteUserById(UserInfo userInfo) {

        userInfoMapper.deleteByPrimaryKey(userInfo);
    }

    @Override
    public void deleteUserByCriteria() {

    }

    @Override
    public List<UserAddress> getUserAddressByUserId(String userId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }
}
