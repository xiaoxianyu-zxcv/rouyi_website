package com.ruoyi.website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.website.domain.entity.Category;

import java.util.List;

/**
 * 分类服务接口
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 分页查询分类列表
     * @param category 查询条件
     * @param page 分页参数
     * @return 分页结果
     */
    Page<Category> selectCategoryPage(Category category, Page<Category> page);

    /**
     * 查询指定栏目下的分类列表
     * @param columnId 栏目ID
     * @return 分类列表
     */
    List<Category> selectCategoryListByColumnId(Long columnId);

    /**
     * 获取分类详情，包含栏目名称
     * @param id 分类ID
     * @return 分类信息
     */
    Category getCategoryWithColumnById(Long id);
}
