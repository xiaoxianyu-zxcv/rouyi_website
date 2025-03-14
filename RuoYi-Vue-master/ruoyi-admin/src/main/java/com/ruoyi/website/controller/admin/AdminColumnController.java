package com.ruoyi.website.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.website.controller.WebsiteBaseController;
import com.ruoyi.website.domain.entity.Column;
import com.ruoyi.website.service.IColumnService;
import com.ruoyi.website.utils.WebsitePageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 栏目管理控制器
 */
@RestController
@RequestMapping("/admin/website/column")
public class AdminColumnController extends WebsiteBaseController {

    @Autowired
    private IColumnService columnService;

    /**
     * 获取栏目列表
     */
    @PreAuthorize("@ss.hasPermi('website:column:list')")
    @GetMapping("/list")
    public AjaxResult list(Column column) {
        Page<Column> page = WebsitePageUtils.startPage();
        page = columnService.selectColumnPage(column, page);
        return getDataTable(page);
    }

    /**
     * 获取栏目详情
     */
    @PreAuthorize("@ss.hasPermi('website:column:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        Column column = columnService.getColumnById(id);
        return AjaxResult.success(column);
    }

    /**
     * 新增栏目
     */
    @PreAuthorize("@ss.hasPermi('website:column:add')")
    @Log(title = "栏目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Column column) {
        // 默认排序值
        if (column.getSort() == null) {
            column.setSort(1);
        }
        // 默认状态为启用
        if (column.getStatus() == null) {
            column.setStatus(1);
        }
        // 默认顶级栏目
        if (column.getPid() == null) {
            column.setPid(0L);
        }

        boolean success = columnService.save(column);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 修改栏目
     */
    @PreAuthorize("@ss.hasPermi('website:column:edit')")
    @Log(title = "栏目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Column column) {
        boolean success = columnService.updateById(column);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 删除栏目
     */
    @PreAuthorize("@ss.hasPermi('website:column:remove')")
    @Log(title = "栏目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        // 这里可以添加检查是否有子栏目或关联文章的逻辑
        boolean success = columnService.removeByIds(java.util.Arrays.asList(ids));
        return toAjax(success ? 1 : 0);
    }
}
