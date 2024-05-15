package com.serene.platform.system.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 菜单树 视图对象类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
@Data
public class MenuTreeVO {

    private String id;
    private String parentId;
    private String path;
    private String name;
    private String component;

    private MetaVO meta;
    private List<MenuTreeVO> children;


}

