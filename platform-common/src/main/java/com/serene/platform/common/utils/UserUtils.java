package com.serene.platform.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.serene.platform.common.entity.MyUserDetails;
import com.serene.platform.common.exception.SessionExpiredException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description: 用户工具类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:53
 */
@Slf4j
@UtilityClass
public final class UserUtils {

    /**
     * 获取当前用户
     */
    public static JSONObject getCurrentUser() {
        // 从SpringSecurity中获取当前用户
        MyUserDetails myUserDetails = null;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                myUserDetails = (MyUserDetails) principal;
            } else {
                // 获取到为匿名用户时，抛出异常
                throw new SessionExpiredException("会话失效");
            }
        } catch (Exception e) {
            // 未获取到凭证,抛出会话超时异常
            throw new SessionExpiredException("会话失效");
        }
        // 获取账号
        String account = myUserDetails.getUsername();
        // 从缓存中获取用户
        CacheUtils cacheUtils = SpringUtils.getBean(CacheUtils.class);
        String userJson = cacheUtils.get(account);
        if (StringUtils.isNotEmpty(userJson)) {
            return JSON.parseObject(userJson);
        } else {
            // 缓存无记录,抛出会话超时异常
            throw new SessionExpiredException("会话失效");
        }

    }

    /**
     * 获取用户id
     */
    public static String getId() {
        JSONObject user = getCurrentUser();
        return user.getString("id");
    }

    /**
     * 获取账号
     */
    public static String getAccount() {
        JSONObject user = getCurrentUser();
        return user.getString("account");
    }

    /**
     * 获取用户姓名
     */
    public static String getName() {
        JSONObject user = getCurrentUser();
        return user.getString("name");
    }

    /**
     * 获取用户所在组织机构标识
     */
    public static String getOrganizationId() {
        JSONObject user = getCurrentUser();
        return user.getString("organization");
    }

    /**
     * 获取用户所在组织机构名称
     */
    public static String getOrganizationName() {
        JSONObject user = getCurrentUser();
        return user.getString("organizationName");
    }


    /**
     * 获取用户所在模块标识
     */
    public static String getModuleId() {
        JSONObject user = getCurrentUser();
        return user.getString("moduleId");
    }

    /**
     * 获取用户所在部门标识
     * 如有多级部门，只取直接上级部门
     */
    public static String getDepartmentId() {
        JSONObject user = getCurrentUser();
        return user.getString("departmentId");
    }

    /**
     * 获取用户所在公司标识
     */
    public static String getCompanyId() {
        JSONObject user = getCurrentUser();
        return user.getString("companyId");
    }

    /**
     * 获取用户所在集团标识
     */
    public static String getGroupId() {
        JSONObject user = getCurrentUser();
        return user.getString("groupId");
    }

}
