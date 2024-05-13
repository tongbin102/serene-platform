package com.serene.platform.common.modules.system.api;

import com.serene.platform.common.modules.system.params.LogDTO;

/**
 * @Description: 日志服务
 * @Author: bin.tong
 * @Date: 2024/5/13 16:49
 */
public interface LogApi {
    /**
     * 添加日志
     *
     * @param logDTO
     */
    void add(LogDTO logDTO);
}
