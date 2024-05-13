package com.serene.platform.common.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Description: 自定义权限对象
 * @Author: bin.tong
 * @Date: 2024/5/13 16:32
 */
@Data
public class MyGrantedAuthority implements GrantedAuthority {

    private String permissionType;
    private String id;
    private String parentId;
    private String code;
    private String pathCode;
    private String name;
    private String icon;
    private String orderNo;


    @Override
    public String getAuthority() {
        return this.pathCode;
    }
}
