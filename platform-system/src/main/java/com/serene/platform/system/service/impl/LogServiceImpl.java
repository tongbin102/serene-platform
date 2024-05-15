package com.serene.platform.system.service.impl;

import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.utils.UserUtils;
import com.serene.platform.common.utils.WebUtils;
import com.serene.platform.system.entity.Log;
import com.serene.platform.system.mapper.LogMapper;
import com.serene.platform.system.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 日志 服务实现类
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
@Service
@Slf4j
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements LogService {
    @Override
    public Log init() {
        Log entity = new Log();
        
        return entity;
    }

    @Override
    protected void beforeAdd(Log entity) {
        try {
            // 设置操作者
            entity.setOperatorId(UserUtils.getId());
            // 设置操作者账号
            entity.setOperatorAccount(UserUtils.getAccount());
            // 设置操作者姓名
            entity.setOperatorName(UserUtils.getName());
        } catch (Exception ex) {
            // 当执行登录操作时，无法正常获取到当前登录人信息，为正常情况
            entity.setOperatorId("");
            entity.setOperatorName("");
            entity.setOperatorAccount("");
        }
        // 设置IP,实时获取，否则一号多登情况下从登陆信息中获取可能不准
        entity.setOperatorIp(WebUtils.getRemoteAddr());
    }

    @Override
    public void beforeModify(Log entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Log> list = this.lambdaQuery().in(Log::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getContent());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Log entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setContent(entity.getContent() + " 副本");
    }

}

