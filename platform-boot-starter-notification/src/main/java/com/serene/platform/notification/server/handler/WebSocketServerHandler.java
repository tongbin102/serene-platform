package com.serene.platform.notification.server.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.serene.platform.common.utils.JwtUtils;
import com.serene.platform.common.utils.SpringUtils;
import com.serene.platform.notification.entity.SystemMessage;
import com.serene.platform.notification.enums.SystemMessageTypeEnums;
import com.serene.platform.notification.server.global.ClientHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 业务处理
 * @Author: bin.tong
 * @Date: 2024/5/15 14:29
 */
@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 获取消息字符串
        String messageString = ((TextWebSocketFrame) textWebSocketFrame).text();


        // 转换为对象
        SystemMessage message = JSONObject.parseObject(messageString, SystemMessage.class);
        // 根据消息类型分别进行处理
        SystemMessageTypeEnums messageType = SystemMessageTypeEnums.valueOf(message.getType());
        SystemMessage response;
        switch (messageType) {
            case LOGIN_REQUEST:
                // 登录请求，鉴权
                String token = message.getContent();
                if (StringUtils.isNotBlank(token)) {
                    JwtUtils jwtUtil = SpringUtils.getBean(JwtUtils.class);
                    jwtUtil.verifyToken(token);
                    // 获取用户账号
                    String account = jwtUtil.decode(token).getSubject();

                    // 添加到全局
                    ClientHolder.addChannel(channelHandlerContext.channel(), account);
                    // 发送登录响应
                    response = new SystemMessage();
                    response.setType(SystemMessageTypeEnums.LOGIN_RESPONSE.name());
                    channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));
                }
                break;
            case HEARTBEAT_REQUEST:
                // 心跳请求，发送心跳响应
                response = new SystemMessage();
                response.setType(SystemMessageTypeEnums.HEARTBEAT_RESPONSE.name());
                channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(response)));
                break;

            case LOGOUT_REQUEST:
                // 注销请求
                channelHandlerContext.channel().close();
                ClientHolder.removeChannel(channelHandlerContext.channel());
                log.info("服务端收到客户端注销请求消息，关闭连接");
                break;
            default:
        }


    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端建立连接，通道开启！");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端断开连接，通道关闭！");
        ClientHolder.removeChannel(ctx.channel());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常", cause);
        ctx.close();
    }
}
