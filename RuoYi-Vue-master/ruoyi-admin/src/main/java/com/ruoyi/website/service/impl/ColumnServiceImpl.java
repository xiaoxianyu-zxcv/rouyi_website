package com.ruoyi.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.website.domain.entity.Column;
import com.ruoyi.website.service.IColumnService;
import com.ruoyi.website.system.repository.ColumnMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 栏目服务实现类
 */
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements IColumnService {

    @Override
    public Page<Column> selectColumnPage(Column column, Page<Column> page) {
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (column != null) {
            // 按名称模糊查询
            if (StringUtils.hasText(column.getName())) {
                wrapper.like(Column::getName, column.getName());
            }

            // 按状态查询
            if (column.getStatus() != null) {
                wrapper.eq(Column::getStatus, column.getStatus());
            }

            // 按父ID查询
            if (column.getPid() != null) {
                wrapper.eq(Column::getPid, column.getPid());
            }
        }

        // 排序
        wrapper.orderByAsc(Column::getSort);

        return this.page(page, wrapper);
    }

    @Override
    public List<Column> selectEnabledColumnList() {
        LambdaQueryWrapper<Column> wrapper = new LambdaQueryWrapper<>();
        // 只查询启用状态的栏目
        wrapper.eq(Column::getStatus, 1);
        wrapper.orderByAsc(Column::getSort);
        return this.list(wrapper);
    }

    @Override
    public Column getColumnById(Long id) {
        return this.getById(id);
    }
}
