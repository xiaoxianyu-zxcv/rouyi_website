package com.ruoyi.website.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 独立的MapperScan配置
 * 可以通过属性控制是否启用此配置
 */
@Configuration
@ConditionalOnProperty(name = "website.mapper-scan.enabled", havingValue = "true", matchIfMissing = false)
@MapperScan("com.ruoyi.website.system.repository.**.mapper")
public class WebsiteMapperScanConfig {
    // 无需额外配置，仅提供MapperScan
}
