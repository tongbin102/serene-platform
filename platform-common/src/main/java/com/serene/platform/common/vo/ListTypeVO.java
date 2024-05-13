package com.serene.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 列表类型视图对象
 * @Author: bin.tong
 * @Date: 2024/5/13 17:01
 */
@Data
public class ListTypeVO {
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

    /**
     * 列表项
     */
    private List<ListItemVO> items;
}
