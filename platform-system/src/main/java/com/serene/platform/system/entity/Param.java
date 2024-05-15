package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 系统参数 实体类
 * @Author: bin.tong
 * @Date: 2024/5/15 11:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_param")
public class Param extends BaseEntity {

    /**
     * 参数名称
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 参数编码
     */
    @TableField("param_key")
    private String paramKey;

    /**
     * 参数值
     */
    @TableField("param_value")
    private String paramValue;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
