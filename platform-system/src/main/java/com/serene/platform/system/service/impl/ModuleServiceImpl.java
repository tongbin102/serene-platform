package com.serene.platform.system.service.impl;

import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.exception.CommonException;
import com.serene.platform.common.exception.CustomException;
import com.serene.platform.system.entity.Module;
import com.serene.platform.system.mapper.ModuleMapper;
import com.serene.platform.system.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 模块 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
@Slf4j
public class ModuleServiceImpl extends BaseServiceImpl<ModuleMapper, Module> implements ModuleService {


    @Override
    public Module init() {
        Module entity = new Module();
        // 默认值处理
        entity.setApp("");
        entity.setRemark("");
        return entity;
    }

    @Override
    public void beforeAdd(Module entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Module::getName, entity.getName())
                .eq(Module::getApp, entity.getApp()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(Module::getCode, entity.getCode())
                .eq(Module::getApp, entity.getApp()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
        // 验证 缩略码 同节点下唯一
        long countAbbreviation = this.lambdaQuery().eq(Module::getAbbreviation, entity.getAbbreviation())
                .eq(Module::getApp, entity.getApp()).count();
        if (countAbbreviation > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【缩略码】");
        }
    }

    @Override
    public void beforeModify(Module entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Module::getName, entity.getName())
                .eq(Module::getApp, entity.getApp())
                .ne(Module::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(Module::getCode, entity.getCode())
                .eq(Module::getApp, entity.getApp())
                .ne(Module::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }

    }

    @Override
    public void beforeRemove(Module entity) {

    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Module> list = this.lambdaQuery().in(Module::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }


    @Override
    protected void copyPropertyHandle(Module entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }


}

