package com.serene.platform.common.vo;

import lombok.Data;

/**
 * @Description: 分页信息
 * @Author: bin.tong
 * @Date: 2024/5/13 17:01
 */
@Data
public class PageInfo {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 每页记录数
     */
    private Integer firstDataIndex;

    public Integer getFirstDataIndex() {
        return (pageNum - 1) * pageSize;
    }
}
