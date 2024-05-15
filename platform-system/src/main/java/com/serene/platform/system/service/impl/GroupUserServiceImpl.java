package com.serene.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.system.entity.GroupUser;
import com.serene.platform.system.mapper.GroupUserMapper;
import com.serene.platform.system.service.GroupUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 用户组与用户对应表 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUserMapper, GroupUser> implements GroupUserService {
    @Override
    public GroupUser init() {
        GroupUser entity = new GroupUser();

        return entity;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveGroup(String userId, List<String> groupIdList) {
        // 先清空用户拥有的所有用户组
        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupUser::getUserId, userId);
        remove(queryWrapper);
        // 再重新插入当前选择角色
        if (CollectionUtils.isNotEmpty(groupIdList)) {
            for (String groupId : groupIdList) {
                GroupUser groupUser = new GroupUser();
                groupUser.setUserId(userId);
                groupUser.setGroupId(groupId);
                add(groupUser);
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(String groupId, List<String> userIdList) {
        for (String userId : userIdList) {
            // 判断是否已存在
            QueryWrapper<GroupUser> roleUserQueryWrapper = new QueryWrapper<>();
            roleUserQueryWrapper.lambda().eq(GroupUser::getUserId, userId).eq(GroupUser::getGroupId, groupId);
            long count = count(roleUserQueryWrapper);
            if (count == 0) {
                // 不存在，则新增
                GroupUser groupUser = new GroupUser();
                groupUser.setGroupId(groupId);
                groupUser.setUserId(userId);
                add(groupUser);
            }
        }
    }
}
