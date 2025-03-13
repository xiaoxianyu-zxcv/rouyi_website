package com.ruoyi.website.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.website.domain.entity.WebsiteBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文章实体类
 * 对应cms_article表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cms_article")
public class Article extends WebsiteBaseEntity {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 缩略图URL
     */
    private String thumbnail;

    /**
     * 栏目ID
     */
    private Long columnId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 状态（0草稿，1已发布）
     */
    private Integer status;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 是否置顶（0否，1是）
     */
    private Integer isTop;

    /**
     * 是否推荐（0否，1是）
     */
    private Integer isRecommend;

    /**
     * 来源
     */
    private String source;

    /**
     * 作者
     */
    private String author;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 栏目名称（非表字段）
     */
    @TableField(exist = false)
    private String columnName;

    /**
     * 分类名称（非表字段）
     */
    @TableField(exist = false)
    private String categoryName;
}
