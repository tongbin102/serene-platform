package com.serene.platform.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description: 标识模型
 * @Author: bin.tong
 * @Date: 2024/5/13 16:14
 */
@Data
public class BaseIdEntity {
    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


}
