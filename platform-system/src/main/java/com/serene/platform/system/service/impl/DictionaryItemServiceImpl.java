package com.serene.platform.system.service.impl;

import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.constants.CacheConstants;
import com.serene.platform.common.enums.StatusEnums;
import com.serene.platform.common.exception.CommonException;
import com.serene.platform.common.exception.CustomException;
import com.serene.platform.system.entity.DictionaryItem;
import com.serene.platform.system.mapper.DictionaryItemMapper;
import com.serene.platform.system.service.DictionaryItemService;
import com.serene.platform.system.service.DictionaryTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 字典项 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
@Slf4j
public class DictionaryItemServiceImpl extends BaseServiceImpl<DictionaryItemMapper, DictionaryItem> implements DictionaryItemService {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;


    @Override
    public DictionaryItem init() {
        DictionaryItem entity = new DictionaryItem();
        // 默认值处理
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(DictionaryItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DictionaryItem::getName, entity.getName())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DictionaryItem::getCode, entity.getCode())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(DictionaryItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DictionaryItem::getName, entity.getName())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType())
                    .ne(DictionaryItem::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DictionaryItem::getCode, entity.getCode())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType())
                    .ne(DictionaryItem::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<DictionaryItem> list = this.lambdaQuery().in(DictionaryItem::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }


    @Override
    protected void copyPropertyHandle(DictionaryItem entity, String... value) {
        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级,设置类型
            entity.setDictionaryType(value[0]);
        } else {
            // 直接复制,主属性后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }
    }


    @Override
    public void afterAdd(DictionaryItem entity) {
        cacheUtils.set(CacheConstants.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId(), entity.getName());
        String typeCode = getTypeCode(entity.getDictionaryType());
        cacheUtils.set(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode(), entity.getName());
        cacheUtils.set(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getName(),
                entity.getCode());

    }

    @Override
    public void afterModify(DictionaryItem entity, DictionaryItem originEntity) {
        cacheUtils.set(CacheConstants.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId(), entity.getName());
        String typeCode = getTypeCode(entity.getDictionaryType());
        cacheUtils.set(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode(), entity.getName());
        cacheUtils.set(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getName(),
                entity.getCode());
    }

    @Override
    public void afterRemove(DictionaryItem entity) {
        cacheUtils.remove(CacheConstants.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId());
        String typeCode = getTypeCode(entity.getDictionaryType());
        cacheUtils.remove(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode());
        cacheUtils.remove(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getName());
    }


    private String getTypeCode(String typeId) {
        return dictionaryTypeService.query(typeId).getCode();
    }

    @Override
    public void enable(String id) {
        DictionaryItem entity = query(id);
        entity.setStatus(StatusEnums.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        DictionaryItem entity = query(id);
        entity.setStatus(StatusEnums.DEAD.name());
        modify(entity);
    }

}

