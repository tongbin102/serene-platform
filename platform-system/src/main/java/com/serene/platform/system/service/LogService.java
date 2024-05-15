package com.serene.platform.system.service;

import com.serene.platform.common.base.BaseService;
import com.serene.platform.system.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * @Description: 日志 服务接口类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
public interface LogService extends BaseService<Log> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);
}
