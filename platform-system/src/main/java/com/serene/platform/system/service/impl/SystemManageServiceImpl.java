package com.serene.platform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.serene.platform.common.constants.CacheConstants;
import com.serene.platform.common.utils.CacheUtils;
import com.serene.platform.system.entity.DictionaryItem;
import com.serene.platform.system.entity.DictionaryType;
import com.serene.platform.system.service.DictionaryItemService;
import com.serene.platform.system.service.DictionaryTypeService;
import com.serene.platform.system.service.SystemManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 系统管理 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
public class SystemManageServiceImpl implements SystemManageService {
    @Autowired
    private CacheUtils cacheUtils;

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @Autowired
    private DictionaryItemService dictionaryItemService;


    @Override

    public void rebuildSystemCache() {


        // 移除字典类型所有的缓存
        cacheUtils.removePrefix(CacheConstants.DICTIONARY_TYPE_CACHE_PREFIX);
        // 获取所有的字典类型数据
        List<DictionaryType> dictionaryTypeList = dictionaryTypeService.list();

        Map<String, String> dictionaryTypeMap = new HashMap<String, String>(200);
        for (DictionaryType entity : dictionaryTypeList) {
            dictionaryTypeMap.put(CacheConstants.DICTIONARY_TYPE_CACHE_PREFIX + entity.getId(), entity.getName());
        }
        // 批量缓存字典类型数据
        cacheUtils.setBatch(dictionaryTypeMap);

        // 移除字典项目所有的缓存
        cacheUtils.removePrefix(CacheConstants.DICTIONARY_ITEM_CACHE_PREFIX);
        // 获取所有的字典项目数据
        List<DictionaryItem> dictionaryItemList = dictionaryItemService.list();

        Map<String, String> dictionaryItemMap = new HashMap<String, String>(2000);
        for (DictionaryItem entity : dictionaryItemList) {
            dictionaryItemMap.put(CacheConstants.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId(), entity.getName());
        }
        // 批量缓存字典项目数据
        cacheUtils.setBatch(dictionaryItemMap);


        // 移除字典项目编码所有的缓存
        cacheUtils.removePrefix(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX);
        Map<String, String> dictionaryItemCodeMap = new HashMap<String, String>(2000);
        for (DictionaryItem entity : dictionaryItemList) {
            String typeCode = dictionaryTypeService.query(entity.getDictionaryType()).getCode();
            dictionaryItemCodeMap.put(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode(), entity.getName());
            dictionaryItemCodeMap.put(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_"
                    + entity.getName(), entity.getCode());

        }
        // 批量缓存字典项目数据
        cacheUtils.setBatch(dictionaryItemCodeMap);
    }

    @Override
    public String getUniqueId() {
        return IdWorker.getIdStr();
    }
}
