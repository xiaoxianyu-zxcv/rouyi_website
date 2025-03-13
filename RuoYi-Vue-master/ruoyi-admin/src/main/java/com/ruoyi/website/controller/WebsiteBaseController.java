package com.ruoyi.website.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * 官网模块基础控制器
 * 使用组合而非继承方式与原系统保持隔离
 */
public class WebsiteBaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 日期格式初始化
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * MyBatis-Plus专用分页结果包装
     *
     * @param page 分页对象
     * @return 统一响应结果
     */
    protected <T> AjaxResult getDataTable(Page<T> page) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("total", page.getTotal());
        ajax.put("rows", page.getRecords());
        return ajax;
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }
}
