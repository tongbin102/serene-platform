package com.serene.platform.common.query;

import lombok.Data;

/**
 * @Description: 查询条件
 * @Author: bin.tong
 * @Date: 2024/5/13 15:58
 */
@Data
public class QueryCondition {

    /**
     * 字段名
     */
    private String field;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 规则类型
     */
    private String rule;
    /**
     * 值
     */
    private String value;
}
