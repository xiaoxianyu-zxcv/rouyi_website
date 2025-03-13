package com.ruoyi.website.common.handler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.ruoyi.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebsiteExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebsiteExceptionHandler.class);

    /**
     * 处理MyBatis-Plus乐观锁异常
     */
    @ExceptionHandler(com.baomidou.mybatisplus.core.exceptions.MybatisPlusException.class)
    public AjaxResult handleMybatisPlusException(MybatisPlusException e) {
        log.error(e.getMessage(), e);
        // 检查是否是乐观锁异常
        if (e.getMessage().contains("Optimistic Locking")) {
            return AjaxResult.error("数据已被其他用户修改，请刷新后重试");
        }
        return AjaxResult.error("数据库操作异常");
    }
}
