package com.serene.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.serene.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description: 日志 实体类
 * @Author: bin.tong
 * @Date: 2024/5/15 11:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_log")
public class Log extends BaseEntity {

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 日志类型
     */
    @TableField("log_type")
    private String logType;

    /**
     * 请求时间
     */
    @TableField("request_time")
    private LocalDateTime requestTime;

    /**
     * 请求参数
     */
    @TableField("request_param")
    private String requestParam;

    /**
     * 请求路径
     */
    @TableField("request_path")
    private String requestPath;

    /**
     * 请求方法
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 操作人标识
     */
    @TableField("operator_id")
    private String operatorId;

    /**
     * 操作人账号
     */
    @TableField("operator_account")
    private String operatorAccount;

    /**
     * 操作人姓名
     */
    @TableField("operator_name")
    private String operatorName;

    /**
     * 操作人ip
     */
    @TableField("operator_ip")
    private String operatorIp;

    /**
     * 响应结果
     */
    @TableField("response_code")
    private String responseCode;

    /**
     * 响应数据
     */
    @TableField("response_data")
    private String responseData;

    /**
     * 执行耗时ms
     */
    @TableField("time_consuming")
    private Integer timeConsuming;

}
