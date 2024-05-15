package com.serene.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.constants.TreeDefaultConstants;
import com.serene.platform.common.enums.StatusEnums;
import com.serene.platform.common.exception.CommonException;
import com.serene.platform.common.exception.CustomException;
import com.serene.platform.notification.service.SystemMessageService;
import com.serene.platform.system.entity.Organization;
import com.serene.platform.system.entity.User;
import com.serene.platform.system.exception.OrganizationExceptionEnums;
import com.serene.platform.system.mapper.OrganizationMapper;
import com.serene.platform.system.service.OrganizationService;
import com.serene.platform.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 组织机构 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
@Slf4j
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemMessageService systemMessageService;

    @Override
    public Organization init() {
        Organization entity = new Organization();
        // 默认值处理
        entity.setType("DEPARTMENT");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(Organization entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Organization::getName, entity.getName())
                .eq(Organization::getOrganization, entity.getOrganization()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 全局唯一
        long countCode = this.lambdaQuery().eq(Organization::getCode, entity.getCode()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
        }
    }

    @Override
    public void beforeModify(Organization entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Organization::getName, entity.getName())
                .eq(Organization::getOrganization, entity.getOrganization())
                .ne(Organization::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 全局唯一
        long countCode = this.lambdaQuery().eq(Organization::getCode, entity.getCode())
                .ne(Organization::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Organization> list = this.lambdaQuery().in(Organization::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void afterModify(Organization entity, Organization orginEntity) {
        // TODO 测试数据，当组织机构名称变更后，发送系统通知
        if (!entity.getName().equals(orginEntity.getName())) {
            String content = MessageFormat.format("{0}变更为{1}", orginEntity.getName(), entity.getName());
            systemMessageService.sendMessage("admin", "组织机构修改", content);
        }

    }

    @Override
    public void beforeRemove(Organization entity) {
        // 验证是否有下级
        if (super.lambdaQuery().eq(Organization::getOrganization, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.HAS_CHILDREN);
        }

        // 验证是否存在人员
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getOrganization, entity.getId());
        long count = userService.count(queryWrapper);
        if (count > 0) {
            throw new CustomException(OrganizationExceptionEnums.HAS_USER);
        }
    }

    @Override
    protected void copyPropertyHandle(Organization entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    public void enable(String id) {
        Organization entity = query(id);
        entity.setStatus(StatusEnums.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        Organization entity = query(id);
        entity.setStatus(StatusEnums.DEAD.name());
        modify(entity);
    }

    @Override
    public String getFullName(String id) {
        // 逐级查找组织机构类型
        List<Organization> list = this.list();
        String currentOrganizationId = id;
        StringBuilder organizationFullName = new StringBuilder();

        do {
            // 取当前组织机构
            Organization currentOrganization = null;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(currentOrganizationId)) {
                    currentOrganization = list.get(i);
                    break;
                }
            }
            organizationFullName.insert(0, currentOrganization.getName()).insert(0, "/");

            // 指向上级
            currentOrganizationId = currentOrganization.getOrganization();
        }
        while (!currentOrganizationId.equals(TreeDefaultConstants.DEFAULT_TREE_ROOT_PARENT_ID));
        return organizationFullName.toString();
    }

    @Override
    public List<String> getParentId(String organizationId) {
        List<String> list = new ArrayList<>(10);

        Organization entity = null;
        do {
            //获取当前实体
            entity = getEntity(organizationId);
            //添加到集合
            list.add(organizationId);
            //将当前实体父标识设置为实体标识
            organizationId = entity.getOrganization();
        }
        while (!TreeDefaultConstants.DEFAULT_TREE_ROOT_PARENT_ID.equals(organizationId));
        return list;
    }


}

