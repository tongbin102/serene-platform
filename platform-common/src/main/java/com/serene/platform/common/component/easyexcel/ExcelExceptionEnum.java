package com.serene.platform.common.component.easyexcel;

import com.serene.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * @Description: excel异常枚举
 * @author: bin.tong
 * @CreateDate: 2024/5/13
 */
@Getter
public enum ExcelExceptionEnum implements ExceptionInterface {


    /**
     * 导出方法未实现
     */
    EXPORT_METHOD_UNIMPLEMENTED("导出方法未实现"),

    ;
    private String message;

    ExcelExceptionEnum(String message) {
        this.message = message;
    }

}
