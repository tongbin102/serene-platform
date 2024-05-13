package com.serene.platform.common.base;

import com.serene.platform.common.query.QueryGenerator;
import com.serene.platform.common.utils.CacheUtils;
import com.serene.platform.common.utils.DictionaryUtils;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @Description: 控制器基类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:22
 */
public class BaseController {

    @Autowired
    protected MapperFacade mapperFacade;

    @Autowired
    protected QueryGenerator queryGenerator;

    @Autowired
    protected DictionaryUtils dictionaryUtil;

    @Autowired
    protected CacheUtils cacheUtils;


    /**
     * 分页
     *
     * @param binder
     */
    @InitBinder("pageInfo")
    public void initPageInfo(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("page_");
    }

    /**
     * 排序
     *
     * @param binder
     */
    @InitBinder("sortInfo")
    public void initAdmin(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("sort_");
    }


}
