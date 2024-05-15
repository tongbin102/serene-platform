package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 权限项 实体类
 * @Author: bin.tong
 * @Date: 2024/5/15 11:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_permission_item")
public class PermissionItem extends BaseEntity {

    /**
     * 上级
     */
    @TableField("permission_item")
    private String permissionItem;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;


    /**
     * 视图编码
     */
    @TableField("view_code")
    private String viewCode;

    /**
     * 组件
     */
    @TableField("component")
    private String component;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

}
