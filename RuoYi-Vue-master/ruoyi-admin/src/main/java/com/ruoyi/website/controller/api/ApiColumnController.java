package com.ruoyi.website.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.website.controller.WebsiteBaseController;
import com.ruoyi.website.domain.entity.Column;
import com.ruoyi.website.service.IColumnService;
import com.ruoyi.website.utils.WebsitePageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台栏目信息API
 */
@RestController
@RequestMapping("/api/website/column")
public class ApiColumnController extends WebsiteBaseController {

    @Autowired
    private IColumnService columnService;

    /**
     * 获取栏目列表
     */
    @GetMapping("/list")
    public AjaxResult list(Column column) {
        Page<Column> page = WebsitePageUtils.startPage();
        page = columnService.selectColumnPage(column, page);
        return getDataTable(page);
    }

    /**
     * 获取所有可用栏目
     */
    @GetMapping("/listEnabled")
    public AjaxResult listEnabled() {
        List<Column> columns = columnService.selectEnabledColumnList();
        return AjaxResult.success(columns);
    }

    /**
     * 获取栏目详情
     */
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        Column column = columnService.getColumnById(id);
        return AjaxResult.success(column);
    }
}
