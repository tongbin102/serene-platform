package com.serene.platform.common.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 流程表单基类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:19
 */
@Data
public class BaseFlowBill extends BaseEntity {


    /**
     * 发起人
     */
    private String initiateUserId;
    /**
     * 发起人姓名
     */
    private String initiateUserName;
    /**
     * 发起人电话
     */
    private String initiateUserPhone;

    /**
     * 发起部门
     */
    private String initiateOrganizationId;
    /**
     * 发起部门名称
     */
    private String initiateOrganizationName;
    /**
     * 发起部门全称
     */
    private String initiateOrganizationFullName;

    /**
     * 发起时间
     */
    private LocalDateTime initiateTime;


    /**
     * 流程类型名称
     */
    private String flowTypeName;
    /**
     * 流程实例标识
     */
    private String flowInstanceId;

    /**
     * 流程状态
     */
    private String flowStatus;

}
