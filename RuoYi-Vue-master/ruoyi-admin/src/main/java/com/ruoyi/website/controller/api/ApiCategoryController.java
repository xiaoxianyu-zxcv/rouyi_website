package com.ruoyi.website.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.website.controller.WebsiteBaseController;
import com.ruoyi.website.domain.entity.Category;
import com.ruoyi.website.service.ICategoryService;
import com.ruoyi.website.utils.WebsitePageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台分类信息API
 */
@RestController
@RequestMapping("/api/website/category")
public class ApiCategoryController extends WebsiteBaseController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public AjaxResult list(Category category) {
        Page<Category> page = WebsitePageUtils.startPage();
        page = categoryService.selectCategoryPage(category, page);
        return getDataTable(page);
    }

    /**
     * 获取指定栏目下的分类列表
     */
    @GetMapping("/listByColumn/{columnId}")
    public AjaxResult listByColumn(@PathVariable Long columnId) {
        List<Category> categories = categoryService.selectCategoryListByColumnId(columnId);
        return AjaxResult.success(categories);
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        Category category = categoryService.getCategoryWithColumnById(id);
        return AjaxResult.success(category);
    }
}
