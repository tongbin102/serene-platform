package com.serene.platform.system.service;

import com.serene.platform.common.base.BaseService;
import com.serene.platform.system.entity.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 系统参数 服务接口类
 * @Author: bin.tong
 * @CreateDate: 2024/5/14
 */
public interface ParamService extends BaseService<Param> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 获取参数对象
     *
     * @param key
     * @return
     */
    Param getParamByKey(String key);


    /**
     * 获取参数值
     *
     * @param key
     * @return
     */
    String getParamValue(String key);


}

