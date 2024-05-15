package com.serene.platform.system.vo;


import com.serene.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 用户配置 视图对象类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserProfileVO extends BaseVO {
    /**
     * 用户
     */
    private String user;

    /**
     * 语种
     */
    @NotBlank(message = "【语种】不能为空")
    private String language;

    /**
     * 时区
     */
    @NotBlank(message = "【时区】不能为空")
    private String timeZone;

    /**
     * 桌面配置
     */
    private String desktopConfig;


    /********非库表存储属性*****/


    /********字典类*****/
    /**
     * 语种
     */
    private String languageName;

    /**
     * 时区
     */
    private String timeZoneName;


    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/


}
