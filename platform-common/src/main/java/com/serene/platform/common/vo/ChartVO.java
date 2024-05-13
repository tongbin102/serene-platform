package com.serene.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 图表 视图对象
 * @Author: bin.tong
 * @Date: 2024/5/13 16:59
 */
@Data
public class ChartVO<T> {
    private List<String> columns;

    private List<T> rows;
}