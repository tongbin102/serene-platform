package com.serene.platform.system.service;

import com.serene.platform.common.base.BaseService;
import com.serene.platform.system.entity.UserGroup;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户组 服务接口类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
public interface UserGroupService extends BaseService<UserGroup> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 启用
     *
     * @param id 标识
     */
    void enable(String id);

    /**
     * 停用
     *
     * @param id 标识
     */
    void disable(String id);

}

