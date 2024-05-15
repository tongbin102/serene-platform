package com.serene.platform.system.exception;

import com.serene.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * @Description: 系统参数相关异常
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Getter
public enum ParamExceptionEnums implements ExceptionInterface {
    /**
     * 没有找到该参数
     */
    NOT_EXIST("没有找到该参数"),
    /**
     * 编码已存在
     */
    CODE_EXIST("编码已存在"),
    /**
     * 名称已存在
     */
    NAME_EXIST("名称已存在"),

    ;


    private String message;

    ParamExceptionEnums(String message) {
        this.message = message;
    }

}
