package com.ruoyi.website.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * MyBatis-Plus分页工具类
 */
public class WebsitePageUtils {

    // 分页参数
    private static final String PAGE_NUM = "pageNum";
    private static final String PAGE_SIZE = "pageSize";

    /**
     * 构建MyBatis-Plus分页对象
     */
    public static <T> Page<T> startPage() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        int pageNum = Integer.parseInt(request.getParameter(PAGE_NUM) != null ? request.getParameter(PAGE_NUM) : "1");
        int pageSize = Integer.parseInt(request.getParameter(PAGE_SIZE) != null ? request.getParameter(PAGE_SIZE) : "10");

        return new Page<>(pageNum, pageSize);
    }
}
