package com.serene.platform.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.serene.platform.system.entity.PermissionItem;
import com.serene.platform.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 用户 Mapper 接口
 * @Author: bin.tong
 * @CreateDate: 2024/5/15
 */
public interface UserMapper extends BaseMapper<User> {


    /**
     * 获取用户权限
     *
     * @param id 用户标识
     * @return 用户权限列表
     */
    List<PermissionItem> getPermission(String id);

    /**
     * 验证权限
     *
     * @param id             用户标识
     * @param permissionCode 权限编码
     * @return 查询结果数量
     */
    int checkPermission(@Param("id") String id, @Param("permissionCode") String permissionCode);
}

