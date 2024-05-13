package com.serene.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 级联框视图对象
 * @Author: bin.tong
 * @Date: 2024/5/13 16:59
 */
@Data
public class CascaderItemVO {
    private String value;
    private String label;
    private List<CascaderItemVO> children;
}
