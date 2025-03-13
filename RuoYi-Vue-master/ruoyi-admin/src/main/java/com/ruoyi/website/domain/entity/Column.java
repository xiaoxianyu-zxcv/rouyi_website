package com.ruoyi.website.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 栏目实体类
 * 对应cms_column表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cms_column")
public class Column extends WebsiteBaseEntity {

    /**
     * 栏目名称
     */
    private String name;

    /**
     * SEO标题
     */
    private String seoTitle;

    /**
     * SEO描述
     */
    private String seoDesc;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态（0禁用，1正常）
     */
    private Integer status;

    /**
     * 父栏目ID，顶级栏目为0
     */
    private Long pid;
}
