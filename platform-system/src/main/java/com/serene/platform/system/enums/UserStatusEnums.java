package com.serene.platform.system.enums;

import lombok.Getter;

/**
 * @Description: 用户状态
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Getter
public enum UserStatusEnums {

    /**
     * 正常
     */
    NORMAL,
    /**
     * 停用
     */
    DEAD,
    /**
     * 锁定
     */
    LOCK,
    ;

}
