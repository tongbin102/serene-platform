package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description: 用户 实体类
 * @Author: bin.tong
 * @Date: 2024/5/15 11:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends BaseEntity {

    /**
     * 组织机构
     */
    @TableField("organization")
    private String organization;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private LocalDateTime birthday;

    /**
     * 手机号
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 职位
     */
    @TableField("position")
    private String position;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 强制修改密码
     */
    @TableField("force_change_password_flag")
    private String forceChangePasswordFlag;

    /**
     * 失败登录次数
     */
    @TableField("fail_login_count")
    private Integer failLoginCount;

    /**
     * 锁定时间
     */
    @TableField(value = "lock_time", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime lockTime;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;


    // region 扩展常用属性
    /**
     * 模块标识
     */
    @TableField(exist = false)
    private String moduleId;

    /**
     * 部门标识
     */
    @TableField(exist = false)
    private String departmentId;

    /**
     * 公司标识
     */
    @TableField(exist = false)
    private String companyId;
    /**
     * 集团标识
     */
    @TableField(exist = false)
    private String groupId;
    /**
     * 组织机构名称
     */
    @TableField(exist = false)
    private String organizationName;
    /**
     * 组织机构全称（带路径）
     */
    @TableField(exist = false)
    private String organizationFullName;

    // endregion

}
