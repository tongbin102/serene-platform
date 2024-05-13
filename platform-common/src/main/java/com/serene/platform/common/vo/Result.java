package com.serene.platform.common.vo;

import lombok.Data;

/**
 * @Description: 返回给前端的处理结果
 * @Author: bin.tong
 * @Date: 2024/5/13 16:36
 */
@Data
public class Result {


    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

}
