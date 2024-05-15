package com.serene.platform.system.service;

import com.serene.platform.common.base.BaseService;
import com.serene.platform.system.entity.GroupPermissionItem;

import java.util.List;

/**
 * @Description: 用户组与权限项对应表 服务类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
public interface GroupPermissionItemService extends BaseService<GroupPermissionItem> {

    /**
     * 保存用户组权限项
     *
     * @param groupId              用户组标识
     * @param permissionItemIdList 权限项标识列表
     */
    void savePermission(String groupId, List<String> permissionItemIdList);


}
