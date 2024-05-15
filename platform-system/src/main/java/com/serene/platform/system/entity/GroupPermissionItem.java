package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseMapEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 用户组与权限项对应表
 * @Author: bin.tong
 * @Date: 2024/5/15 11:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_group_permission_item")
public class GroupPermissionItem extends BaseMapEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组标识
     */
    @TableField("group_id")
    private String groupId;

    /**
     * 权限项标识
     */
    @TableField("permission_item_id")
    private String permissionItemId;

}
