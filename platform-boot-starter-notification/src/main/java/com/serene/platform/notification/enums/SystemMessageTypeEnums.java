package com.serene.platform.notification.enums;

import lombok.Getter;

/**
 * @Description: 消息类型
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Getter
public enum SystemMessageTypeEnums {
    /**
     * 登录请求
     */
    LOGIN_REQUEST,

    /**
     * 登录响应
     */
    LOGIN_RESPONSE,

    /**
     * 心跳请求
     */
    HEARTBEAT_REQUEST,

    /**
     * 心跳响应
     */
    HEARTBEAT_RESPONSE,

    /**
     * 业务消息
     */
    BUSINESS_MESSAGE,


    /**
     * 注销请求
     */
    LOGOUT_REQUEST,

    ;


}
