package com.serene.platform.common.enums;

import lombok.Getter;

/**
 * @Description: 系统日志类型
 * @author: bin.tong
 * @CreateDate: 2024/5/13
 */
@Getter
public enum LogTypeEnums {
    /**
     * 操作日志
     */
    OPERATION,
    /**
     * 审计日志
     */
    AUDIT,
    /**
     * 调度日志
     */
    SCHEDULER;

}
