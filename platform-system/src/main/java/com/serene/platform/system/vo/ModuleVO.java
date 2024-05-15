package com.serene.platform.system.vo;


import com.serene.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 模块 视图对象类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ModuleVO extends BaseVO {
    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空")
    private String code;

    /**
     * 应用
     */
    @NotBlank(message = "应用不能为空")
    private String app;

    /**
     * 缩略码
     */
    @NotBlank(message = "缩略码不能为空")
    private String abbreviation;

    /**
     * 包路径
     */
    @NotBlank(message = "包路径不能为空")
    private String packagePath;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序号
     */
    private String orderNo;


    /********字典类*****/
    /**
     * 应用
     */
    private String appName;

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
