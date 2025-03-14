package com.ruoyi.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.website.domain.entity.Category;
import com.ruoyi.website.domain.entity.Column;
import com.ruoyi.website.service.ICategoryService;
import com.ruoyi.website.service.IColumnService;
import com.ruoyi.website.system.repository.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 分类服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private IColumnService columnService;

    @Override
    public Page<Category> selectCategoryPage(Category category, Page<Category> page) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (category != null) {
            // 按名称模糊查询
            if (StringUtils.hasText(category.getName())) {
                wrapper.like(Category::getName, category.getName());
            }

            // 按状态查询
            if (category.getStatus() != null) {
                wrapper.eq(Category::getStatus, category.getStatus());
            }

            // 按栏目ID查询
            if (category.getColumnId() != null) {
                wrapper.eq(Category::getColumnId, category.getColumnId());
            }
        }

        // 排序
        wrapper.orderByAsc(Category::getSort);

        Page<Category> resultPage = this.page(page, wrapper);

        // 填充栏目名称
        for (Category cat : resultPage.getRecords()) {
            if (cat.getColumnId() != null) {
                Column column = columnService.getById(cat.getColumnId());
                if (column != null) {
                    cat.setColumnName(column.getName());
                }
            }
        }

        return resultPage;
    }

    @Override
    public List<Category> selectCategoryListByColumnId(Long columnId) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getColumnId, columnId);
        wrapper.eq(Category::getStatus, 1); // 只查询启用的分类
        wrapper.orderByAsc(Category::getSort);
        return this.list(wrapper);
    }

    @Override
    public Category getCategoryWithColumnById(Long id) {
        Category category = this.getById(id);
        if (category != null && category.getColumnId() != null) {
            Column column = columnService.getById(category.getColumnId());
            if (column != null) {
                category.setColumnName(column.getName());
            }
        }
        return category;
    }
}
