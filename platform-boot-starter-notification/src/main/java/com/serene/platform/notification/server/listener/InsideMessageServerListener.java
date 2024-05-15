package com.serene.platform.notification.server.listener;

import com.serene.platform.notification.server.InsideMessageServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Description: NettyServer监听器，用于启动消息服务
 * @Author: bin.tong
 * @Date: 2024/5/15 14:45
 */
@WebListener
public class InsideMessageServerListener implements ServletContextListener {

    /**
     * 注入NettyServer
     */
    @Autowired
    private InsideMessageServer nettyServer;

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Thread thread = new Thread(new NettyServerThread());
        // 启动netty服务
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * netty服务启动线程
     */
    private class NettyServerThread implements Runnable {

        @Override
        public void run() {
            nettyServer.run();
        }
    }

}
