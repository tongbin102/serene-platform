package com.serene.platform.notification.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 通知配置文件
 * @Author: bin.tong
 * @Date: 2024/5/15 14:38
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.notification")
public class NotificationConfig {

    /**
     * 服务端口
     */
    private Integer serverPort = 9997;


}
