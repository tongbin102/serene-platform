package com.serene.platform.notification.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serene.platform.common.annotation.SystemLog;
import com.serene.platform.common.base.BaseController;
import com.serene.platform.common.query.QueryGenerator;
import com.serene.platform.common.utils.ResultUtils;
import com.serene.platform.common.utils.UserUtils;
import com.serene.platform.common.vo.PageInfo;
import com.serene.platform.common.vo.Result;
import com.serene.platform.common.vo.SortInfo;
import com.serene.platform.notification.entity.SystemMessage;
import com.serene.platform.notification.service.SystemMessageService;
import com.serene.platform.notification.vo.SystemMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 系统消息 前端控制器类
 * @Author: bin.tong
 * @Date: 2024/5/15 14:38
 */
@RestController
@RequestMapping("/notification/systemMessage")
@Slf4j
public class SystemMessageController extends BaseController {
    @Autowired
    private SystemMessageService systemMessageService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        SystemMessage entity = systemMessageService.init();
        SystemMessageVO vo = convert2VO(entity);
        return ResultUtils.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "系统消息-新增")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody SystemMessageVO vo) {
        SystemMessage entity = convert2Entity(vo);
        systemMessageService.add(entity);
        SystemMessageVO newVO = convert2VO(entity);
        return ResultUtils.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "系统消息-修改")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody SystemMessageVO vo) {
        SystemMessage entity = convert2Entity(vo);
        systemMessageService.modify(entity);
        SystemMessageVO newVO = convert2VO(entity);
        return ResultUtils.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "系统消息-删除")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        systemMessageService.remove(id);
        return ResultUtils.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "系统消息-分页")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:query')")
    public ResponseEntity<Result> page(SystemMessageVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<SystemMessage> page = new Page<SystemMessage>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 附加当前用户
        queryVO.setReceiver(UserUtils.getAccount());

        // 构造查询条件
        QueryWrapper<SystemMessage> queryWrapper = QueryGenerator.generateQueryWrapper(SystemMessage.class, queryVO, sortInfo);

        // 查询数据
        systemMessageService.page(page, queryWrapper);
        // 转换vo
        IPage<SystemMessageVO> pageVO = mapperFacade.map(page, IPage.class);
        List<SystemMessageVO> systemMessageVOList = convert2VO(page.getRecords());
        pageVO.setRecords(systemMessageVOList);
        return ResultUtils.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "系统消息-列表")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:query')")
    public ResponseEntity<Result> list(SystemMessageVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<SystemMessage> queryWrapper = QueryGenerator.generateQueryWrapper(SystemMessage.class, queryVO, sortInfo);
        List<SystemMessage> list = systemMessageService.list(queryWrapper);
        // 转换vo
        List<SystemMessageVO> systemMessageVOList = convert2VO(list);
        return ResultUtils.success(systemMessageVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "系统消息-详情")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        SystemMessage entity = systemMessageService.query(id);
        SystemMessageVO vo = convert2VO(entity);
        return ResultUtils.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "系统消息-复制新增")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        systemMessageService.addByCopy(id);
        return ResultUtils.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 获取未读消息数量
     */

    @GetMapping("/getUnreadMessageCount")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:query')")
    public ResponseEntity<Result> getUnreadMessageCount() {
        Long count = systemMessageService.getUnreadMessageCount();
        return ResultUtils.success(count);
    }


    /**
     * 设置已读
     */

    @PutMapping("/{id}/read")
    @SystemLog(value = "消息-设置单条已读")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:view')")
    public ResponseEntity<Result> setRead(@PathVariable("id") String id) {

        SystemMessage entity = systemMessageService.setRead(id);
        SystemMessageVO newVO = convert2VO(entity);
        return ResultUtils.success(newVO);
    }

    /**
     * 设置所有已读
     */
    @PutMapping("/setAllRead")
    @SystemLog(value = "消息-设置所有已读")
    @PreAuthorize("hasPermission(null,'notification:systemMessage:setAllRead')")
    public ResponseEntity<Result> setAllRead() {

        systemMessageService.setAllRead();
        return ResultUtils.success();
    }

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link SystemMessageVO} 视图对象
     */
    private SystemMessageVO convert2VO(SystemMessage entity) {
        SystemMessageVO vo = mapperFacade.map(entity, SystemMessageVO.class);
        vo.setTypeName(dictionaryUtil.getNameByCode("SystemMessageType", entity.getType()));
        vo.setReadFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getReadFlag()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link SystemMessageVO}> 视图对象列表
     */
    private List<SystemMessageVO> convert2VO(List<SystemMessage> entityList) {
        List<SystemMessageVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            SystemMessageVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private SystemMessage convert2Entity(SystemMessageVO vo) {
        SystemMessage entity = mapperFacade.map(vo, SystemMessage.class);
        return entity;
    }

    // endregion
}
