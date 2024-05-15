package com.serene.platform.system.api;

import com.serene.platform.common.modules.system.api.LogApi;
import com.serene.platform.common.modules.system.params.LogDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author: bin.tong
 * @Date: 2024/5/14 13:51
 */
@Component
public class LogApiImpl implements LogApi {

    @Autowired
    private LogService logService;

    @Override
    public void add(LogDTO logDTO) {
        Log log = new Log();
        BeanUtils.copyProperties(logDTO, log);
        logService.add(log);

    }
}
