package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description: 用户密码修改日志
 * @Author: bin.tong
 * @Date: 2024/5/15 11:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_password_change_log")
public class UserPasswordChangeLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户标识
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码更新时间
     */
    @TableField("change_time")
    private LocalDateTime changeTime;

    /**
     * 原密码
     */
    @TableField("origin_password")
    private String originPassword;

}
