package com.serene.platform.notification.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.serene.platform.common.base.BaseServiceImpl;
import com.serene.platform.common.enums.YesOrNoEnums;
import com.serene.platform.common.utils.UserUtils;
import com.serene.platform.notification.entity.SystemMessage;
import com.serene.platform.notification.mapper.SystemMessageMapper;
import com.serene.platform.notification.server.global.ClientHolder;
import com.serene.platform.notification.service.SystemMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 系统消息 服务实现类
 * @Author: bin.tong
 * @Date: 2024/5/15 14:25
 */
@Service
@Slf4j
public class SystemMessageServiceImpl extends BaseServiceImpl<SystemMessageMapper, SystemMessage> implements SystemMessageService {
    @Override
    public SystemMessage init() {
        SystemMessage entity = new SystemMessage();
        // 默认值处理
        entity.setType("BUSINESS_MESSAGE");
        entity.setReadFlag("NO");
        return entity;
    }

    @Override
    public void beforeAdd(SystemMessage entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(SystemMessage entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<SystemMessage> list = this.lambdaQuery().in(SystemMessage::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getTitle());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(SystemMessage entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setTitle(entity.getTitle() + " 副本");
    }


    @Override
    public void sendMessage(String account, String title, String content) {
        SystemMessage entity = init();
        entity.setReceiver(account);
        entity.setTitle(title);
        entity.setContent(content);
        add(entity);
        ClientHolder.sendMessage(entity);

    }

    @Override
    public SystemMessage setRead(String id) {
        SystemMessage entity = getEntity(id);
        entity.setReadFlag(YesOrNoEnums.YES.name());
        modify(entity);
        return entity;
    }

    @Override
    public void setAllRead() {
        LambdaUpdateWrapper<SystemMessage> lambdaUpdateWrapper = new UpdateWrapper<SystemMessage>().lambda().set(SystemMessage::getReadFlag,
                        YesOrNoEnums.YES.name())
                .eq(SystemMessage::getReceiver, UserUtils.getAccount())
                .eq(SystemMessage::getReadFlag, YesOrNoEnums.NO.name());
        this.update(lambdaUpdateWrapper);
    }

    @Override
    public Long getUnreadMessageCount() {
        return this.lambdaQuery().eq(SystemMessage::getReceiver, UserUtils.getAccount())
                .eq(SystemMessage::getReadFlag, YesOrNoEnums.NO.name())
                .count();
    }
}
