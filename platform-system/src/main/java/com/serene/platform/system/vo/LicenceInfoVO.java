package com.serene.platform.system.vo;

import lombok.Data;

/**
 * @Description: 授权信息 视图对象类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
@Data
public class LicenceInfoVO {

    /**
     * 申请码
     */
    private String applyCode;

    /**
     * 授权码
     */
    private String licenceCode;

    /**
     * 授权是否有效
     */
    private String validFlag;

    /**
     * 授权无效原因
     */
    private String invalidReason;
}
