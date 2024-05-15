package com.serene.platform.system.service;

import com.serene.platform.common.base.BaseService;
import com.serene.platform.system.entity.DictionaryItem;
import com.serene.platform.system.entity.DictionaryType;

import java.util.List;
import java.util.Map;

/**
 * @Description: 字典类型 服务接口类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
public interface DictionaryTypeService extends BaseService<DictionaryType> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);


    /**
     * 获取指定字典编码下所有字典项列表
     * 用于公用选择控件
     *
     * @param typeCode 字典类型编码
     * @return 字典项列表
     */
    List<DictionaryItem> getItem(String typeCode);


    /**
     * 保存字典项
     *
     * @param id       字典类型标识
     * @param itemList 字典项列表
     */
    void save(String id, List<DictionaryItem> itemList);


    /**
     * 通过代码获取详情
     *
     * @param code 代码
     * @return {@link DictionaryType}
     */
    DictionaryType getByCode(String code);
}

