package com.ruoyi.website.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.website.controller.WebsiteBaseController;
import com.ruoyi.website.domain.entity.Article;
import com.ruoyi.website.service.IArticleService;
import com.ruoyi.website.service.ICategoryService;
import com.ruoyi.website.service.IColumnService;
import com.ruoyi.website.utils.WebsitePageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 文章管理控制器
 */
@RestController
@RequestMapping("/admin/website/article")
public class AdminArticleController extends WebsiteBaseController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IColumnService columnService;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 获取文章列表
     */
    @PreAuthorize("@ss.hasPermi('website:article:list')")
    @GetMapping("/list")
    public AjaxResult list(Article article) {
        Page<Article> page = WebsitePageUtils.startPage();
        page = articleService.selectArticlePage(article, page);
        return getDataTable(page);
    }

    /**
     * 获取文章详情
     */
    @PreAuthorize("@ss.hasPermi('website:article:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        Article article = articleService.getArticleWithColumnAndCategoryById(id);
        return AjaxResult.success(article);
    }

    /**
     * 新增文章
     */
    @PreAuthorize("@ss.hasPermi('website:article:add')")
    @Log(title = "文章管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Article article) {
        // 参数校验
        if (article.getColumnId() == null) {
            return AjaxResult.error("栏目ID不能为空");
        }

        // 检查栏目是否存在
        if (columnService.getById(article.getColumnId()) == null) {
            return AjaxResult.error("所选栏目不存在");
        }

        // 检查分类是否存在
        if (article.getCategoryId() != null && categoryService.getById(article.getCategoryId()) == null) {
            return AjaxResult.error("所选分类不存在");
        }

        // 设置默认值
        if (article.getStatus() == null) {
            article.setStatus(0); // 默认为草稿状态
        }

        if (article.getViewCount() == null) {
            article.setViewCount(0);
        }

        if (article.getIsTop() == null) {
            article.setIsTop(0);
        }

        if (article.getIsRecommend() == null) {
            article.setIsRecommend(0);
        }

        if (article.getSort() == null) {
            article.setSort(1);
        }

        // 如果是发布状态，设置发布时间
        if (article.getStatus() == 1 && article.getPublishTime() == null) {
            article.setPublishTime(LocalDateTime.now());
        }

        // 如果摘要为空，自动生成摘要
        if (article.getSummary() == null && article.getContent() != null) {
            // 从内容中提取前100个字符作为摘要
            String content = article.getContent().replaceAll("<[^>]+>", ""); // 移除HTML标签
            if (content.length() > 100) {
                article.setSummary(content.substring(0, 100) + "...");
            } else {
                article.setSummary(content);
            }
        }

        boolean success = articleService.save(article);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 修改文章
     */
    @PreAuthorize("@ss.hasPermi('website:article:edit')")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Article article) {
        // 参数校验
        if (article.getId() == null) {
            return AjaxResult.error("文章ID不能为空");
        }

        // 检查文章是否存在
        Article existArticle = articleService.getById(article.getId());
        if (existArticle == null) {
            return AjaxResult.error("文章不存在");
        }

        // 检查栏目是否存在
        if (article.getColumnId() != null && columnService.getById(article.getColumnId()) == null) {
            return AjaxResult.error("所选栏目不存在");
        }

        // 检查分类是否存在
        if (article.getCategoryId() != null && categoryService.getById(article.getCategoryId()) == null) {
            return AjaxResult.error("所选分类不存在");
        }

        // 如果从草稿变为发布状态，设置发布时间
        if (existArticle.getStatus() == 0 && article.getStatus() != null && article.getStatus() == 1) {
            article.setPublishTime(LocalDateTime.now());
        }

        // 如果摘要为空，自动生成摘要
        if (article.getSummary() == null && article.getContent() != null) {
            // 从内容中提取前100个字符作为摘要
            String content = article.getContent().replaceAll("<[^>]+>", ""); // 移除HTML标签
            if (content.length() > 100) {
                article.setSummary(content.substring(0, 100) + "...");
            } else {
                article.setSummary(content);
            }
        }

        boolean success = articleService.updateById(article);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 删除文章
     */
    @PreAuthorize("@ss.hasPermi('website:article:remove')")
    @Log(title = "文章管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        boolean success = articleService.removeByIds(java.util.Arrays.asList(ids));
        return toAjax(success ? 1 : 0);
    }

    /**
     * 发布文章
     */
    @PreAuthorize("@ss.hasPermi('website:article:edit')")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PutMapping("/publish/{id}")
    public AjaxResult publish(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null) {
            return AjaxResult.error("文章不存在");
        }

        article.setStatus(1);
        article.setPublishTime(LocalDateTime.now());

        boolean success = articleService.updateById(article);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 设置/取消置顶
     */
    @PreAuthorize("@ss.hasPermi('website:article:edit')")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PutMapping("/toggleTop/{id}")
    public AjaxResult toggleTop(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null) {
            return AjaxResult.error("文章不存在");
        }

        // 切换置顶状态
        article.setIsTop(article.getIsTop() == 1 ? 0 : 1);

        boolean success = articleService.updateById(article);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 设置/取消推荐
     */
    @PreAuthorize("@ss.hasPermi('website:article:edit')")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PutMapping("/toggleRecommend/{id}")
    public AjaxResult toggleRecommend(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null) {
            return AjaxResult.error("文章不存在");
        }

        // 切换推荐状态
        article.setIsRecommend(article.getIsRecommend() == 1 ? 0 : 1);

        boolean success = articleService.updateById(article);
        return toAjax(success ? 1 : 0);
    }
}
