package com.serene.platform.common.vo;

import lombok.Data;

/**
 * @Description: 列表项视图对象
 * @Author: bin.tong
 * @Date: 2024/5/13 17:01
 */
@Data
public class ListItemVO {
    /**
     * 标识
     */
    private String value;
    /**
     * 名称
     */
    private String label;

    /**
     * 编码
     */
    private String code;
}
