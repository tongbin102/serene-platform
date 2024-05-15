package com.serene.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.enums.StatusEnums;
import com.serene.platform.common.exception.CommonException;
import com.serene.platform.common.exception.CustomException;
import com.serene.platform.system.entity.GroupPermissionItem;
import com.serene.platform.system.entity.GroupUser;
import com.serene.platform.system.entity.UserGroup;
import com.serene.platform.system.mapper.UserGroupMapper;
import com.serene.platform.system.service.GroupPermissionItemService;
import com.serene.platform.system.service.GroupUserService;
import com.serene.platform.system.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户组 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
@Slf4j
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    @Autowired
    private GroupPermissionItemService groupPermissionItemService;

    @Autowired
    private GroupUserService groupUserService;


    @Override
    public UserGroup init() {
        UserGroup entity = new UserGroup();
        // 默认值处理
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(UserGroup entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(UserGroup::getName, entity.getName())
                    .eq(UserGroup::getUserGroup, entity.getUserGroup()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(UserGroup::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(UserGroup entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(UserGroup::getName, entity.getName())
                    .eq(UserGroup::getUserGroup, entity.getUserGroup())
                    .ne(UserGroup::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(UserGroup::getCode, entity.getCode())
                    .ne(UserGroup::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }


    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<UserGroup> list = this.lambdaQuery().in(UserGroup::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(UserGroup entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    public void beforeRemove(UserGroup entity) {
        // 移除用户组对应的权限
        QueryWrapper<GroupPermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupPermissionItem::getGroupId, entity.getId());
        groupPermissionItemService.remove(queryWrapper);

        // 移除用户组对应的用户
        QueryWrapper<GroupUser> queryWrapperGroupUser = new QueryWrapper<>();
        queryWrapperGroupUser.lambda().eq(GroupUser::getGroupId, entity.getId());
        groupUserService.remove(queryWrapperGroupUser);
    }

    @Override
    public void enable(String id) {
        UserGroup entity = getEntity(id);
        entity.setStatus(StatusEnums.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        UserGroup entity = getEntity(id);
        entity.setStatus(StatusEnums.DEAD.name());
        modify(entity);
    }

}

