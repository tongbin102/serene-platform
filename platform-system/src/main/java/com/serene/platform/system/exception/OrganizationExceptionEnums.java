package com.serene.platform.system.exception;

import com.serene.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * @Description: 组织机构相关异常
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Getter
public enum OrganizationExceptionEnums implements ExceptionInterface {

    /**
     * 未找到组织机构
     */
    NAME_NOT_FOUND("未找到上级名称"),
    /**
     * 未找到组织机构编码
     */
    CODE_NOT_FOUND("未找到上级编码"),

    /**
     * 编码和名称不能同时为空
     */
    PARENT_NAME_AND_CODE_CANOT_NULL("上级名称和上级编码不能同时为空"),
    /**
     * 组织机构下存在人员，不能删除
     */
    HAS_USER("组织机构下存在人员，不能删除"),
    ;


    private String message;

    OrganizationExceptionEnums(String message) {
        this.message = message;
    }

}
