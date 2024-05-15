package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 模块 实体类
 * @Author: bin.tong
 * @Date: 2024/5/15 11:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_module")
public class Module extends BaseEntity {

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
     * 应用
     */
    @TableField("app")
    private String app;

    /**
     * 缩略码
     */
    @TableField("abbreviation")
    private String abbreviation;

    /**
     * 包路径
     */
    @TableField("package_path")
    private String packagePath;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
