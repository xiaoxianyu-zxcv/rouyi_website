package com.ruoyi.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.website.domain.entity.Article;
import com.ruoyi.website.domain.entity.Category;
import com.ruoyi.website.domain.entity.Column;
import com.ruoyi.website.service.IArticleService;
import com.ruoyi.website.service.ICategoryService;
import com.ruoyi.website.service.IColumnService;
import com.ruoyi.website.system.repository.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 文章服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private IColumnService columnService;

    @Autowired
    private ICategoryService categoryService;

    @Override
    public Page<Article> selectArticlePage(Article article, Page<Article> page) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (article != null) {
            // 按标题模糊查询
            if (StringUtils.hasText(article.getTitle())) {
                wrapper.like(Article::getTitle, article.getTitle());
            }

            // 按栏目ID查询
            if (article.getColumnId() != null) {
                wrapper.eq(Article::getColumnId, article.getColumnId());
            }

            // 按分类ID查询
            if (article.getCategoryId() != null) {
                wrapper.eq(Article::getCategoryId, article.getCategoryId());
            }

            // 按状态查询
            if (article.getStatus() != null) {
                wrapper.eq(Article::getStatus, article.getStatus());
            }

            // 按作者查询
            if (StringUtils.hasText(article.getAuthor())) {
                wrapper.like(Article::getAuthor, article.getAuthor());
            }

            // 按是否置顶查询
            if (article.getIsTop() != null) {
                wrapper.eq(Article::getIsTop, article.getIsTop());
            }

            // 按是否推荐查询
            if (article.getIsRecommend() != null) {
                wrapper.eq(Article::getIsRecommend, article.getIsRecommend());
            }
        }

        // 排序: 置顶优先 > 排序 > 发布时间
        wrapper.orderByDesc(Article::getIsTop);
        wrapper.orderByAsc(Article::getSort);
        wrapper.orderByDesc(Article::getPublishTime);

        Page<Article> resultPage = this.page(page, wrapper);

        // 填充栏目名称和分类名称
        for (Article art : resultPage.getRecords()) {
            enrichArticleWithNames(art);
        }

        return resultPage;
    }

    @Override
    public Page<Article> selectArticlesByColumnId(Long columnId, Page<Article> page) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getColumnId, columnId);
        wrapper.eq(Article::getStatus, 1); // 只查询已发布的文章
        wrapper.orderByDesc(Article::getIsTop);
        wrapper.orderByAsc(Article::getSort);
        wrapper.orderByDesc(Article::getPublishTime);

        Page<Article> resultPage = this.page(page, wrapper);

        // 填充栏目名称和分类名称
        for (Article art : resultPage.getRecords()) {
            enrichArticleWithNames(art);
        }

        return resultPage;
    }

    @Override
    public Page<Article> selectArticlesByCategoryId(Long categoryId, Page<Article> page) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryId, categoryId);
        wrapper.eq(Article::getStatus, 1); // 只查询已发布的文章
        wrapper.orderByDesc(Article::getIsTop);
        wrapper.orderByAsc(Article::getSort);
        wrapper.orderByDesc(Article::getPublishTime);

        Page<Article> resultPage = this.page(page, wrapper);

        // 填充栏目名称和分类名称
        for (Article art : resultPage.getRecords()) {
            enrichArticleWithNames(art);
        }

        return resultPage;
    }

    @Override
    public Article getArticleWithColumnAndCategoryById(Long id) {
        Article article = this.getById(id);
        if (article != null) {
            enrichArticleWithNames(article);
        }
        return article;
    }

    @Override
    public boolean incrementViewCount(Long id) {
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, id);
        updateWrapper.setSql("view_count = view_count + 1");
        return this.update(updateWrapper);
    }

    @Override
    public List<Article> selectRecommendArticles(int limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getIsRecommend, 1);
        wrapper.eq(Article::getStatus, 1); // 只查询已发布的文章
        wrapper.orderByDesc(Article::getPublishTime);
        wrapper.last("LIMIT " + limit);

        List<Article> list = this.list(wrapper);

        // 填充栏目名称和分类名称
        for (Article art : list) {
            enrichArticleWithNames(art);
        }

        return list;
    }

    @Override
    public List<Article> selectTopArticles(int limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getIsTop, 1);
        wrapper.eq(Article::getStatus, 1); // 只查询已发布的文章
        wrapper.orderByAsc(Article::getSort);
        wrapper.orderByDesc(Article::getPublishTime);
        wrapper.last("LIMIT " + limit);

        List<Article> list = this.list(wrapper);

        // 填充栏目名称和分类名称
        for (Article art : list) {
            enrichArticleWithNames(art);
        }

        return list;
    }

    /**
     * 填充文章的栏目名称和分类名称
     * @param article 文章对象
     */
    private void enrichArticleWithNames(Article article) {
        if (article.getColumnId() != null) {
            Column column = columnService.getById(article.getColumnId());
            if (column != null) {
                article.setColumnName(column.getName());
            }
        }

        if (article.getCategoryId() != null) {
            Category category = categoryService.getById(article.getCategoryId());
            if (category != null) {
                article.setCategoryName(category.getName());
            }
        }
    }
}
