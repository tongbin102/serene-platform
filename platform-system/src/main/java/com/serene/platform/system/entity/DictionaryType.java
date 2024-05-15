package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 字典类型 实体类
 * @Author: bin.tong
 * @Date: 2024/5/15 11:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dictionary_type")
public class DictionaryType extends BaseEntity {

    /**
     * 上级
     */
    @TableField("dictionary_type")
    private String dictionaryType;

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
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

}
