package com.atguigu.gmall0325.service;

import com.atguigu.gmall0325.entity.UserAddress;
import com.atguigu.gmall0325.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    /**
     * 查询所有的用户信息
     * @return
     */
    List<UserInfo> selectAll();


    /**
     * 根据id查询用户信息
     * @return
     */
    UserInfo selectUserById(String id);


    /**
     * 根据name查询用户信息
     * @param name
     * @return
     */
    List<UserInfo> selectUserByName(String name);


    /**
     * 输入任意属性查询用户信息
     * @return
     */
    List<UserInfo> selectUserByAll(UserInfo userInfo);


    /**
     * 根据姓名进行模糊查询
     * @return
     */
    List<UserInfo> selectUserLikeName(String name);


    /**
     * 添加用户信息
     * @param userInfo
     */
    void insertUser(UserInfo userInfo);


    /**
     * 根据id修改用户信息
     * @param userInfo
     */
    void updateUserById(UserInfo userInfo);


    /**
     * 根据id删除用户信息
     * @param userInfo
     */
    void deleteUserById(UserInfo userInfo);


    void deleteUserByCriteria();


    /**
     * 根据用户的id查找收货地址列表
     * @param userId
     * @return
     */
    List<UserAddress> getUserAddressByUserId(String userId);

}
