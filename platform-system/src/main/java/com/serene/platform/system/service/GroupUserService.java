package com.serene.platform.system.service;

import com.serene.platform.common.base.BaseService;
import com.serene.platform.system.entity.GroupUser;

import java.util.List;

/**
 * @Description: 用户组与用户对应表 服务类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
public interface GroupUserService extends BaseService<GroupUser> {

    /**
     * 保存用户对应的用户组
     *
     * @param userId      用户标识
     * @param groupIdList 用户组标识列表
     */
    void saveGroup(String userId, List<String> groupIdList);


    /**
     * 为用户组新增用户，如已存在则跳过，不会抛出异常
     *
     * @param groupId    用户组标识
     * @param userIdList 用户标识列表
     */
    void addUser(String groupId, List<String> userIdList);

}
