package com.serene.platform.common.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 允许匿名用户访问
 *
 * @Author: bin.tong
 * @Date: 2024/5/13 15:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@PreAuthorize("isAnonymous()")
public @interface AllowAnonymous {


}
