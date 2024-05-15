package com.serene.platform.system.vo;


import com.serene.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 权限项 视图对象类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionItemVO extends BaseVO {
    /**
     * 上级
     */
    @NotBlank(message = "【上级】不能为空")
    private String permissionItem;

    /**
     * 名称
     */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "【编码】不能为空")
    private String code;

    /**
     * 类型
     */
    @NotBlank(message = "【类型】不能为空")
    private String type;

    /**
     * 权限编码
     */
    private String permissionCode;


    /**
     * 视图编码
     */
    private String viewCode;

    /**
     * 组件
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private String orderNo;


    /********字典类*****/
    /**
     * 类型
     */
    private String typeName;
    /**
     * 状态
     */
    private String statusName;

    /********实体类*****/
    /**
     * 上级
     */
    private String permissionItemName;

    /********范围查询*****/

    /********自定义扩展*****/
    /**
     * 忽略上级
     */
    private Boolean ignoreParent;

    /********子对象*****/


}
