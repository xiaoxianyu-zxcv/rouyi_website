package com.ruoyi.website.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.website.controller.WebsiteBaseController;
import com.ruoyi.website.domain.entity.Article;
import com.ruoyi.website.service.IArticleService;
import com.ruoyi.website.utils.WebsitePageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台文章信息API
 */
@RestController
@RequestMapping("/api/website/article")
public class ApiArticleController extends WebsiteBaseController {

    @Autowired
    private IArticleService articleService;

    /**
     * 获取文章列表
     */
    @GetMapping("/list")
    public AjaxResult list(Article article) {
        // 前台只显示已发布的文章
        article.setStatus(1);
        Page<Article> page = WebsitePageUtils.startPage();
        page = articleService.selectArticlePage(article, page);
        return getDataTable(page);
    }

    /**
     * 获取指定栏目下的文章列表
     */
    @GetMapping("/listByColumn/{columnId}")
    public AjaxResult listByColumn(@PathVariable Long columnId) {
        Page<Article> page = WebsitePageUtils.startPage();
        page = articleService.selectArticlesByColumnId(columnId, page);
        return getDataTable(page);
    }

    /**
     * 获取指定分类下的文章列表
     */
    @GetMapping("/listByCategory/{categoryId}")
    public AjaxResult listByCategory(@PathVariable Long categoryId) {
        Page<Article> page = WebsitePageUtils.startPage();
        page = articleService.selectArticlesByCategoryId(categoryId, page);
        return getDataTable(page);
    }

    /**
     * 获取文章详情
     */
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        Article article = articleService.getArticleWithColumnAndCategoryById(id);

        // 增加浏览次数
        if (article != null) {
            articleService.incrementViewCount(id);
        }

        return AjaxResult.success(article);
    }

    /**
     * 获取推荐文章列表
     */
    @GetMapping("/recommend/{limit}")
    public AjaxResult recommend(@PathVariable Integer limit) {
        // 限制最大数量为20
        if (limit > 20) {
            limit = 20;
        }
        List<Article> articles = articleService.selectRecommendArticles(limit);
        return AjaxResult.success(articles);
    }

    /**
     * 获取置顶文章列表
     */
    @GetMapping("/top/{limit}")
    public AjaxResult top(@PathVariable Integer limit) {
        // 限制最大数量为20
        if (limit > 20) {
            limit = 20;
        }
        List<Article> articles = articleService.selectTopArticles(limit);
        return AjaxResult.success(articles);
    }
}
