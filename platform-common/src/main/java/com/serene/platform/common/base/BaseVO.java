package com.serene.platform.common.base;

import lombok.Data;

/**
 * @Description: 视图对象 基类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:17
 */
@Data
public class BaseVO extends BaseMapVO {


    /**
     * 删除标志
     */

    private String deleteFlag;
}
