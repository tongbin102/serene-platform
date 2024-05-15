package com.serene.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.system.entity.GroupPermissionItem;
import com.serene.platform.system.mapper.GroupPermissionItemMapper;
import com.serene.platform.system.service.GroupPermissionItemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 用户组与权限项对应表 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
public class GroupPermissionItemServiceImpl extends BaseServiceImpl<GroupPermissionItemMapper, GroupPermissionItem> implements GroupPermissionItemService {
    @Override
    public GroupPermissionItem init() {
        GroupPermissionItem entity = new GroupPermissionItem();

        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePermission(String groupId, List<String> permissionItemIdList) {
        // 先清空组拥有的所有权限
        QueryWrapper<GroupPermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupPermissionItem::getGroupId, groupId);
        remove(queryWrapper);
        // 再重新插入当前选择的权限
        if (CollectionUtils.isNotEmpty(permissionItemIdList)) {
            for (String permissionItemId : permissionItemIdList) {
                GroupPermissionItem groupPermissionItem = new GroupPermissionItem();
                groupPermissionItem.setGroupId(groupId);
                groupPermissionItem.setPermissionItemId(permissionItemId);
                add(groupPermissionItem);
            }
        }
    }
}
