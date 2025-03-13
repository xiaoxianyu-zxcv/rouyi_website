package com.ruoyi.website.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus元数据处理器
 * 用于自动填充字段
 */
@Component
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 创建时间自动填充
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        // 更新时间自动填充
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // 创建者ID自动填充（需要从当前登录用户获取）
        // Long userId = SecurityUtils.getCurrentUserId();
        // this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        // this.strictInsertFill(metaObject, "updateBy", Long.class, userId);

        // 初始化逻辑删除标志为0(未删除)
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);

        // 初始化乐观锁版本号为1
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间自动填充
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // 更新者ID自动填充（需要从当前登录用户获取）
        // Long userId = SecurityUtils.getCurrentUserId();
        // this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
    }
}
