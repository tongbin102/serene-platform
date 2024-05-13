package com.serene.platform.common.utils;

import com.serene.platform.common.constants.CacheConstants;
import com.serene.platform.common.enums.DataDictionaryExceptionEnums;
import com.serene.platform.common.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 字典工具类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:22
 */
@Component
public class DictionaryUtils {

    @Autowired
    private CacheUtils cacheUtils;

    /**
     * 根据字典项编码获取字典项名称
     *
     * @param dictionaryTypeCode
     * @param code
     * @return
     */
    public String getNameByCode(String dictionaryTypeCode, String code) {
        if (StringUtils.isNotBlank(code)) {
            String name = cacheUtils.get(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX +
                    "_" + dictionaryTypeCode + "_" + code);
            return name;
        }
        return StringUtils.EMPTY;

    }

    /**
     * 根据字典项名称获取字典项编码
     *
     * @param dictionaryTypeCode
     * @param name
     * @return
     */
    public String getCodeByName(String dictionaryTypeCode, String name) {
        if (StringUtils.isNotBlank(name)) {
            // 通过名称反向查编码
            String code = cacheUtils.get(CacheConstants.DICTIONARY_ITEM_CODE_CACHE_PREFIX +
                    "_" + dictionaryTypeCode + "_" + name);
            if (StringUtils.isNotBlank(code)) {
                return code;
            }
        }
        // 如未找到说明数据不规范，例如通过excel导入数据场景下，因此需要抛出异常
        throw new CustomException(DataDictionaryExceptionEnums.CANT_FIND_DICTIONARY_ITEM_CODE, name);

    }
}
