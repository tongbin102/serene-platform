package com.serene.platform.system.vo;

import lombok.Data;

/**
 * @Description: 用户修改密码 视图对象类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
@Data
public class UserChangePasswordVO {


    /**
     * 用户标识
     */
    private String userId;
    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
