package com.serene.platform.system.vo;


import com.serene.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 系统参数 视图对象类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ParamVO extends BaseVO {
    /**
    * 参数名称
    */
    @NotBlank(message = "【参数名称】不能为空")
    private String paramName;

    /**
    * 参数编码
    */
    @NotBlank(message = "【参数编码】不能为空")
    private String paramKey;

    /**
    * 参数值
    */
    private String paramValue;

    /**
    * 排序号
    */
    private String orderNo;


    /********字典类*****/

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
