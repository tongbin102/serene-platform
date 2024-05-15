package com.serene.platform.notification.vo;

import com.serene.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 系统消息 视图对象类
 * @Author: bin.tong
 * @Date: 2024/5/15 14:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemMessageVO extends BaseVO {
    /**
     * 类型
     */
    @NotBlank(message = "【类型】不能为空")
    private String type;

    /**
     * 接收人
     */
    @NotBlank(message = "【接收人】不能为空")
    private String receiver;

    /**
     * 标题
     */
    @NotBlank(message = "【标题】不能为空")
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否已读
     */
    private String readFlag;


    /********非库表存储属性*****/


    /********字典类*****/
    /**
     * 类型
     */
    private String typeName;

    /**
     * 是否已读
     */
    private String readFlagName;


    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/


}
