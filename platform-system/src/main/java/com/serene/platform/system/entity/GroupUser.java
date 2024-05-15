package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseMapEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 用户组与用户对应表
 * @Author: bin.tong
 * @Date: 2024/5/15 11:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_group_user")
public class GroupUser extends BaseMapEntity {


    /**
     * 组标识
     */
    @TableField("group_id")
    private String groupId;

    /**
     * 用户标识
     */
    @TableField("user_id")
    private String userId;

}
