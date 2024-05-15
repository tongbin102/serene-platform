package com.serene.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.constants.CacheConstants;
import com.serene.platform.common.enums.StatusEnums;
import com.serene.platform.common.exception.CommonException;
import com.serene.platform.common.exception.CustomException;
import com.serene.platform.system.entity.DictionaryItem;
import com.serene.platform.system.entity.DictionaryType;
import com.serene.platform.system.mapper.DictionaryTypeMapper;
import com.serene.platform.system.service.DictionaryItemService;
import com.serene.platform.system.service.DictionaryTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 字典类型 服务实现类
 * @Author bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
@Slf4j
public class DictionaryTypeServiceImpl extends BaseServiceImpl<DictionaryTypeMapper, DictionaryType> implements DictionaryTypeService {
    @Autowired
    private DictionaryItemService dictionaryItemService;

    @Override
    public DictionaryType init() {
        DictionaryType entity = new DictionaryType();
        // 默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(DictionaryType entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DictionaryType::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DictionaryType::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(DictionaryType entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DictionaryType::getName, entity.getName()).ne(DictionaryType::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DictionaryType::getCode, entity.getCode()).ne(DictionaryType::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeRemove(DictionaryType entity) {
        // 删除字典项
        QueryWrapper<DictionaryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryItem::getDictionaryType, entity.getId());
        dictionaryItemService.remove(queryWrapper);
        // 删除下级
        List<DictionaryType> child = this.lambdaQuery().eq(DictionaryType::getDictionaryType, entity.getId()).list();
        for (DictionaryType item : child) {
            remove(item.getId());
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<DictionaryType> list = this.lambdaQuery().in(DictionaryType::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(DictionaryType entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    protected void afterAddByCopy(DictionaryType source, DictionaryType entity) {
        String sourceId = source.getId();
        String targetId = entity.getId();

        QueryWrapper<DictionaryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryItem::getDictionaryType, sourceId);
        List<DictionaryItem> list = dictionaryItemService.list(queryWrapper);
        for (DictionaryItem item : list) {
            dictionaryItemService.addByCopy(item.getId(), targetId);
        }

    }


    @Override
    public void afterRemove(DictionaryType entity) {
        cacheUtils.remove(CacheConstants.DICTIONARY_TYPE_CACHE_PREFIX + entity.getId());

    }

    @Override
    public void afterAdd(DictionaryType entity) {
        cacheUtils.set(CacheConstants.DICTIONARY_TYPE_CACHE_PREFIX + entity.getId(), entity.getName());
    }

    @Override
    public void afterModify(DictionaryType entity, DictionaryType originEntity) {
        cacheUtils.set(CacheConstants.DICTIONARY_TYPE_CACHE_PREFIX + entity.getId(), entity.getName());

        // 更新字典项缓存
        QueryWrapper<DictionaryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryItem::getDictionaryType, entity.getId());
        List<DictionaryItem> list = dictionaryItemService.list(queryWrapper);
        list.stream().forEach(x -> {
            cacheUtils.set(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + entity.getCode() + "_" + x.getCode(), x.getName());

        });


    }


    /**
     * 获取指定字典编码下所有字典项列表
     */
    @Override
    public List<DictionaryItem> getItem(String typeCode) {
        if (StringUtils.isNotBlank(typeCode)) {
            // 查询字典类型，获取id
            QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(DictionaryType::getCode, typeCode);
            DictionaryType dictionaryType = getOne(queryWrapper);
            if (dictionaryType != null) {
                String dictionaryTypeId = dictionaryType.getId();
                // 查询字典项
                QueryWrapper<DictionaryItem> itemQueryWrapper = new QueryWrapper<>();
                itemQueryWrapper.lambda().eq(DictionaryItem::getDictionaryType, dictionaryTypeId).eq(DictionaryItem::getStatus, StatusEnums.NORMAL.toString()).orderByAsc(DictionaryItem::getOrderNo);
                return dictionaryItemService.list(itemQueryWrapper);
            }
        }
        // 未查询到数据情况下，返回空集合，不抛异常
        return new ArrayList<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String id, List<DictionaryItem> itemList) {
        handleDictionaryItem(id, itemList);
    }

    @Override
    public DictionaryType getByCode(String code) {
        QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryType::getCode, code);
        DictionaryType dictionaryType = getOne(queryWrapper);
        return dictionaryType;
    }

    private void handleDictionaryItem(String typeId, List<DictionaryItem> dictionaryItems) {
        //  清理子表
        QueryWrapper<DictionaryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictionaryItem::getDictionaryType, typeId);
        dictionaryItemService.remove(queryWrapper);
        // 更新字典项表
        dictionaryItems.stream().forEach(dictionaryItem -> {
            dictionaryItem.setId(null);
            // 设置父节点
            dictionaryItem.setDictionaryType(typeId);
            dictionaryItemService.add(dictionaryItem);
        });
    }

}

