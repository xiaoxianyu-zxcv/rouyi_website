package com.ruoyi.website.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.website.controller.WebsiteBaseController;
import com.ruoyi.website.domain.entity.Category;
import com.ruoyi.website.service.ICategoryService;
import com.ruoyi.website.service.IColumnService;
import com.ruoyi.website.utils.WebsitePageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理控制器
 */
@RestController
@RequestMapping("/admin/website/category")
public class AdminCategoryController extends WebsiteBaseController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IColumnService columnService;

    /**
     * 获取分类列表
     */
    @PreAuthorize("@ss.hasPermi('website:category:list')")
    @GetMapping("/list")
    public AjaxResult list(Category category) {
        Page<Category> page = WebsitePageUtils.startPage();
        page = categoryService.selectCategoryPage(category, page);
        return getDataTable(page);
    }

    /**
     * 获取分类详情
     */
    @PreAuthorize("@ss.hasPermi('website:category:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        Category category = categoryService.getCategoryWithColumnById(id);
        return AjaxResult.success(category);
    }

    /**
     * 新增分类
     */
    @PreAuthorize("@ss.hasPermi('website:category:add')")
    @Log(title = "分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Category category) {
        // 参数校验
        if (category.getColumnId() == null) {
            return AjaxResult.error("栏目ID不能为空");
        }

        // 检查栏目是否存在
        if (columnService.getById(category.getColumnId()) == null) {
            return AjaxResult.error("所选栏目不存在");
        }

        // 默认排序值
        if (category.getSort() == null) {
            category.setSort(1);
        }

        // 默认状态为启用
        if (category.getStatus() == null) {
            category.setStatus(1);
        }

        boolean success = categoryService.save(category);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 修改分类
     */
    @PreAuthorize("@ss.hasPermi('website:category:edit')")
    @Log(title = "分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Category category) {
        // 参数校验
        if (category.getId() == null) {
            return AjaxResult.error("分类ID不能为空");
        }

        // 检查栏目是否存在
        if (category.getColumnId() != null && columnService.getById(category.getColumnId()) == null) {
            return AjaxResult.error("所选栏目不存在");
        }

        boolean success = categoryService.updateById(category);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 删除分类
     */
    @PreAuthorize("@ss.hasPermi('website:category:remove')")
    @Log(title = "分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        // 这里可以添加检查是否有关联文章的逻辑
        boolean success = categoryService.removeByIds(java.util.Arrays.asList(ids));
        return toAjax(success ? 1 : 0);
    }
}
