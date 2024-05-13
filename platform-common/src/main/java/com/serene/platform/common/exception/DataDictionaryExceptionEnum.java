package com.serene.platform.common.exception;

import lombok.Getter;

/**
 * @Description: 数据字典相关异常
 * @author: bin.tong
 * @CreateDate: 2024/5/13
 */
@Getter
public enum DataDictionaryExceptionEnum implements ExceptionInterface {

    /**
     * 未找到字典项对应的编码
     */
    CANT_FIND_DICTIONARY_ITEM_CODE("未找到字典项【{0}】对应的编码"),
    ;


    private String message;

    DataDictionaryExceptionEnum(String message) {
        this.message = message;
    }

}
