package com.serene.platform.system.service;

import com.serene.platform.common.base.BaseService;
import com.serene.platform.system.entity.UserProfile;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户配置 服务接口类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
public interface UserProfileService extends BaseService<UserProfile> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 获取或初始化
     *
     * @return
     */
    UserProfile getOrInit();
}

