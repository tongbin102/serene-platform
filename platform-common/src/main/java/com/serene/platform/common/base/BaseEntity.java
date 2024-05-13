package com.serene.platform.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @Description: 实体 基类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:15
 */
@Data
public class BaseEntity extends BaseMapEntity {

    /**
     * 逻辑删除
     */
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @TableLogic
    protected String deleteFlag;
}
