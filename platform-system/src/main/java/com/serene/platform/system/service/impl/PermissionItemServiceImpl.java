package com.serene.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.constants.TreeDefaultConstants;
import com.serene.platform.common.enums.StatusEnums;
import com.serene.platform.common.exception.CommonException;
import com.serene.platform.common.exception.CustomException;
import com.serene.platform.common.utils.CacheUtils;
import com.serene.platform.system.entity.GroupPermissionItem;
import com.serene.platform.system.entity.PermissionItem;
import com.serene.platform.system.enums.PermissionTypeEnums;
import com.serene.platform.system.mapper.PermissionItemMapper;
import com.serene.platform.system.service.GroupPermissionItemService;
import com.serene.platform.system.service.PermissionItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 权限项 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
@Slf4j
public class PermissionItemServiceImpl extends BaseServiceImpl<PermissionItemMapper, PermissionItem> implements PermissionItemService {

    @Autowired
    private GroupPermissionItemService groupPermissionItemService;

    @Autowired
    private CacheUtils cacheUtils;


    @Override
    public PermissionItem init() {
        PermissionItem entity = new PermissionItem();
        // 默认值处理
        entity.setType("");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(PermissionItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(PermissionItem::getName, entity.getName())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }

        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(PermissionItem::getCode, entity.getCode())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }

    }

    @Override
    public void beforeModify(PermissionItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(PermissionItem::getName, entity.getName())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem())
                    .ne(PermissionItem::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }

        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(PermissionItem::getCode, entity.getCode())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem())
                    .ne(PermissionItem::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }


    }

    @Override
    protected void afterModify(PermissionItem entity, PermissionItem orginEntity) {
        // 若编码或上级或权限编码发生变化，则级联修改下级权限编码
        if (entity.getCode().equals(orginEntity.getCode()) == false
                || entity.getPermissionItem().equals(orginEntity.getPermissionItem()) == false
                || entity.getPermissionCode().equals(orginEntity.getPermissionCode()) == false) {
            List<PermissionItem> list = this.lambdaQuery().eq(PermissionItem::getPermissionItem, entity.getId()).list();
            if (CollectionUtils.isNotEmpty(list)) {
                for (PermissionItem item : list) {
                    modify(item);
                }
            }
        }
    }

    @Override
    public void beforeRemove(PermissionItem entity) {


        // 若权限已分配用户组，先删除相应的用户组与权限对应关系数据
        QueryWrapper<GroupPermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupPermissionItem::getPermissionItemId, entity.getId());
        long count = groupPermissionItemService.count(queryWrapper);
        if (count > 0) {
            groupPermissionItemService.remove(queryWrapper);
        }

        // 删除子项
        QueryWrapper<PermissionItem> queryWrapperSub = new QueryWrapper<>();
        queryWrapperSub.lambda().eq(PermissionItem::getPermissionItem, entity.getId());
        this.remove(queryWrapperSub);

    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<PermissionItem> list = this.lambdaQuery().in(PermissionItem::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(PermissionItem entity, String... value) {

        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级
            // 设置父级标识
            entity.setPermissionItem(value[0]);

        } else {
            // 直接复制
            // 名称后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }
    }

    @Override
    protected void afterAddByCopy(PermissionItem sourceEntity, PermissionItem entity) {
        // 拷贝下级
        List<PermissionItem> list = this.lambdaQuery().eq(PermissionItem::getPermissionItem, sourceEntity.getId()).list();
        if (CollectionUtils.isNotEmpty(list)) {
            for (PermissionItem item : list) {
                addByCopy(item.getId(), entity.getId());
            }
        }
    }

    @Override
    public void enable(String id) {
        PermissionItem entity = query(id);
        entity.setStatus(StatusEnums.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        PermissionItem entity = query(id);
        entity.setStatus(StatusEnums.DEAD.name());
        modify(entity);
    }

    @Override
    protected void beforeAddOrModifyOp(PermissionItem entity) {
        String permissionCode = generatePermissionCode(entity);
        entity.setPermissionCode(permissionCode);

        if (entity.getType().equals(PermissionTypeEnums.MENU.name()) ||
                entity.getType().equals(PermissionTypeEnums.PAGE.name())
        ) {
            String componentPath = generateComponentPath(entity);
            entity.setComponent(componentPath);
        } else if (entity.getType().equals(PermissionTypeEnums.MODULE.name())) {
            entity.setComponent("#");
        }


    }

    /**
     * 自动生成权限编码
     *
     * @param entity
     * @return
     */
    private String generatePermissionCode(PermissionItem entity) {

        StringBuffer sb = new StringBuffer();

        do {
            sb.insert(0, entity.getCode()).insert(0, ":");
            entity = getById(entity.getPermissionItem());

        } while (!entity.getId().equals(TreeDefaultConstants.DEFAULT_TREE_ROOT_ID));
        return sb.substring(1);
    }

    /**
     * 自动生成组件路径
     *
     * @param entity
     * @return
     */
    private String generateComponentPath(PermissionItem entity) {


        PermissionItem tempEntity = entity;

        String moduleCode = "";
        String folderCode = "";
        String tempCode = "";

        do {
            tempCode = tempEntity.getCode();
            // 若为模块，终止循环
            if (tempEntity.getType().equals(PermissionTypeEnums.MODULE.name())) {
                moduleCode = tempEntity.getCode();
                break;
            }
            // 替换为上级
            tempEntity = getById(tempEntity.getPermissionItem());

            // 若为模块，终止循环
            if (tempEntity.getType().equals(PermissionTypeEnums.MODULE.name())) {
                moduleCode = tempEntity.getCode();
                // 模块直接子级为目录
                folderCode = tempCode;
                break;
            }

        } while (!tempEntity.getPermissionItem().equals(TreeDefaultConstants.DEFAULT_TREE_ROOT_ID));

        return MessageFormat.format("{0}/view/{1}/{2}", tempEntity.getCode(), folderCode, entity.getViewCode());

    }

}

