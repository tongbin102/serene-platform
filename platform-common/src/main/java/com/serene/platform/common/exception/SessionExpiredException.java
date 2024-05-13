package com.serene.platform.common.exception;

import lombok.Data;

/**
 * @Description: 会话超时异常
 * @Author: bin.tong
 * @Date: 2024/5/13 16:37
 */
@Data
public class SessionExpiredException extends RuntimeException {

    public SessionExpiredException(String message) {
        super(message);
    }


}
