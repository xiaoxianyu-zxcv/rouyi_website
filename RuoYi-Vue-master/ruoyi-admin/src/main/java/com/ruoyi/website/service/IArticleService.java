package com.ruoyi.website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.website.domain.entity.Article;

import java.util.List;

/**
 * 文章服务接口
 */
public interface IArticleService extends IService<Article> {

    /**
     * 分页查询文章列表
     * @param article 查询条件
     * @param page 分页参数
     * @return 分页结果
     */
    Page<Article> selectArticlePage(Article article, Page<Article> page);

    /**
     * 查询指定栏目下的文章列表
     * @param columnId 栏目ID
     * @param page 分页参数
     * @return 分页结果
     */
    Page<Article> selectArticlesByColumnId(Long columnId, Page<Article> page);

    /**
     * 查询指定分类下的文章列表
     * @param categoryId 分类ID
     * @param page 分页参数
     * @return 分页结果
     */
    Page<Article> selectArticlesByCategoryId(Long categoryId, Page<Article> page);

    /**
     * 获取文章详情，包含栏目名称和分类名称
     * @param id 文章ID
     * @return 文章信息
     */
    Article getArticleWithColumnAndCategoryById(Long id);

    /**
     * 增加文章浏览次数
     * @param id 文章ID
     * @return 是否成功
     */
    boolean incrementViewCount(Long id);

    /**
     * 获取推荐文章列表
     * @param limit 限制数量
     * @return 文章列表
     */
    List<Article> selectRecommendArticles(int limit);

    /**
     * 获取置顶文章列表
     * @param limit 限制数量
     * @return 文章列表
     */
    List<Article> selectTopArticles(int limit);
}
