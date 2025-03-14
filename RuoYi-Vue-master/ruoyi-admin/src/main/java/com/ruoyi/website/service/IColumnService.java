package com.ruoyi.website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.website.domain.entity.Column;

import java.util.List;

/**
 * 栏目服务接口
 */
public interface IColumnService extends IService<Column> {

    /**
     * 分页查询栏目列表
     * @param column 查询条件
     * @param page 分页参数
     * @return 分页结果
     */
    Page<Column> selectColumnPage(Column column, Page<Column> page);

    /**
     * 查询所有可用栏目
     * @return 栏目列表
     */
    List<Column> selectEnabledColumnList();

    /**
     * 获取栏目详情
     * @param id 栏目ID
     * @return 栏目信息
     */
    Column getColumnById(Long id);
}
