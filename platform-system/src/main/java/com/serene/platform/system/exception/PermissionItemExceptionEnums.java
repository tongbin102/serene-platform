package com.serene.platform.system.exception;

import com.serene.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * @Description: 权限项相关异常
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Getter
public enum PermissionItemExceptionEnums implements ExceptionInterface {


    /**
     * 该权限项当前停用
     */
    STATUS_IS_DEAD("该权限项当前停用"),
    /**
     * 权限项不存在
     */
    NOT_EXIST("权限项不存在"),
    /**
     * 同级下已存在相同名称的权限项目
     */
    PERMISSION_NAME_EXIST("同级下已存在相同名称的权限项目"),
    /**
     * 权限项被分配角色，不能删除
     */
    PERMISSION_HAS_ROLE("权限项被分配角色，不能删除"),
    /**
     * 无权访问，请联系系统管理员
     */
    NO_PERMISSION("无权访问，请联系系统管理员"),
    ;


    private String message;

    PermissionItemExceptionEnums(String message) {
        this.message = message;
    }

}
