package com.serene.platform.system.service;

/**
 * @Description: 系统管理服务
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
public interface SystemManageService {
    /**
     * 重建系统缓存
     */
    void rebuildSystemCache();


    /**
     * 获取唯一标识
     *
     * @return {@link String}
     */
    String getUniqueId();
}
