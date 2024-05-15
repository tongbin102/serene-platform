package com.example.platform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * 注：该模块为开发平台核心包，需作为业务系统的基础包，因此虽然是springboot的结构
 * 但不能加SpringBootApplication注解，并且在打包时需打成普通jar包
 *
 * @author: bin.tong
 * @CreateDate: 2024/5/14
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({PlatformConfig.class})
@Import(SpringUtil.class)
@EnableRetry
@ServletComponentScan
@EnableTransactionManagement
public class SereneBootStarterConfig {

    public static void main(String[] args) {
        SpringApplication.run(SereneBootStarterConfig.class, args);
    }

}
