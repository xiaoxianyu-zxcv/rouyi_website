package com.ruoyi.website.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类实体类
 * 对应cms_category表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cms_category")
public class Category extends WebsiteBaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String content;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态（0禁用，1正常）
     */
    private Integer status;

    /**
     * 所属栏目ID
     */
    private Long columnId;

    /**
     * 栏目名称（非表字段）
     */
    @TableField(exist = false)
    private String columnName;
}
