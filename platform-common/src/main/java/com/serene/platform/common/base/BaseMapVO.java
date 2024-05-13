package com.serene.platform.common.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 映射关系 视图对象
 * @Author: bin.tong
 * @Date: 2024/5/13 16:16
 */
@Data
public class BaseMapVO extends BaseIdVO {


    /**
     * 创建人标识
     */
    private String createId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人标识
     */

    private String updateId;

    /**
     * 更新时间
     */

    private LocalDateTime updateTime;

    /**
     * 版本
     */

    private Integer version;


}
