package com.serene.platform.common.exception;

import java.text.MessageFormat;

/**
 * @Description: 自定义异常
 * @Author: bin.tong
 * @Date: 2024/5/13 16:08
 */
public class CustomException extends RuntimeException {

    /**
     * 继承exception
     */
    public CustomException(ExceptionInterface exceptionInterface) {
        super(exceptionInterface.getMessage());
    }

    /**
     * 错误信息带参数
     */
    public CustomException(ExceptionInterface exceptionInterface, Object... args) {
        super(MessageFormat.format(exceptionInterface.getMessage(), args));

    }
}
