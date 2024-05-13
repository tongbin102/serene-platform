package com.serene.platform.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 树视图模型
 * @Author: bin.tong
 * @Date: 2024/5/13 16:52
 */
@Data
public class TreeVO {

    private String id;
    private String code;
    private String label;
    private Boolean expand;
    private Boolean checked;
    private List<TreeVO> children;
    private String parentId;

}
