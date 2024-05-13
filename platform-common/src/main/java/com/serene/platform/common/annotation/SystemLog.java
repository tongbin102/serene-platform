package com.serene.platform.common.annotation;


import com.serene.platform.common.enums.LogTypeEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 系统操作日志切面注解
 * @Author: bin.tong
 * @Date: 2024/5/13 15:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SystemLog {

    /**
     * 日志内容
     */
    String value();

    /**
     * 日志类型
     */
    LogTypeEnums logType() default LogTypeEnums.OPERATION;

    /**
     * 是否记录请求参数
     */
    boolean logRequestParam() default false;

    /**
     * 是否记录响应数据
     */
    boolean logResponseData() default false;

    /**
     * 执行是否成功
     */
    ExecuteResultEnum executeResult() default ExecuteResultEnum.FAILURE;
}
