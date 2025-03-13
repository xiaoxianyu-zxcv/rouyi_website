package com.example.website.system.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

/**
 * 官网模块的MyBatis-Plus配置类
 *
 * 这个类的作用是:
 * 1. 创建一个独立的SQL会话工厂(SqlSessionFactory)，专门用于官网模块
 * 2. 只扫描官网模块的Mapper接口，不影响其他模块
 * 3. 配置MyBatis-Plus特有的功能(如分页、乐观锁)
 *
 * 通过这种方式，官网模块的数据库访问完全与管理后台隔离，互不影响
 */
@Configuration
// 仅当website.mybatis-plus.enabled=true时，此配置才生效
@ConditionalOnProperty(name = "website.mybatis-plus.enabled", havingValue = "true", matchIfMissing = false)
// 只扫描官网模块的Mapper接口，并使用专用的SqlSessionFactory
@MapperScan(basePackages = "com.ruoyi.website.system.repository.**.mapper", sqlSessionFactoryRef = "websiteSqlSessionFactory")
public class WebsiteMybatisPlusConfig {

    /**
     * 配置MyBatis-Plus插件
     *
     * 这里添加了:
     * 1. 分页插件 - 支持数据分页查询
     * 2. 乐观锁插件 - 支持数据版本控制，防止并发修改冲突
     */
    @Bean
    @ConditionalOnProperty(name = "website.mybatis-plus.enabled", havingValue = "true", matchIfMissing = false)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件，指定数据库类型为MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 创建官网模块专用的SQL会话工厂
     *
     * 这个工厂负责:
     * 1. 连接数据库
     * 2. 管理SQL映射
     * 3. 处理数据库操作
     */
    @Bean("websiteSqlSessionFactory")
    @ConditionalOnProperty(name = "website.mybatis-plus.enabled", havingValue = "true", matchIfMissing = false)
    public SqlSessionFactory websiteSqlSessionFactory(DataSource dataSource, MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        // 设置数据源(复用现有数据源)
        factory.setDataSource(dataSource);
        // 设置实体类别名包
        factory.setTypeAliasesPackage("com.example.website.system.domain");
        // 设置Mapper XML文件位置(官网模块专用目录)
        factory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/website/**/*Mapper.xml"));
        // 设置MyBatis-Plus插件
        factory.setPlugins(mybatisPlusInterceptor);
        return factory.getObject();
    }
}
