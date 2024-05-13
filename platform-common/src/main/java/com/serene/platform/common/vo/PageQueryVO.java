package com.serene.platform.common.vo;

import lombok.Data;

/**
 * @Description: 分页查询视图模型
 * @Author: bin.tong
 * @Date: 2024/5/13 17:02
 */
@Data
public class PageQueryVO<T> {

    /**
     * 分页信息
     */
    private PageInfo page;

    /**
     * 排序信息
     */
    private SortInfo sort;

    /**
     * 实体视图模型
     */
    private T entity;
}
