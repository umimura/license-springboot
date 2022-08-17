package kr.co.vci.license.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = { "kr.co.vci.license.mapper" }, sqlSessionFactoryRef = "jndiSqlSessionFactory")
@ComponentScan(basePackages = { "kr.co.vci.license.service" })
@EnableTransactionManagement
public class JndiContext {

        // dataSource 설정
        @Bean
        public DataSource jndiDataSource() {
                JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
                return jndiDataSourceLookup.getDataSource("java:comp/env/jdbc/postgre");
        }

        // sqlSessionFactory 설정
        @Bean
        public SqlSessionFactoryBean jndiSqlSessionFactory() throws IOException {
                SqlSessionFactoryBean jndiSqlSessionFactory = new SqlSessionFactoryBean();
                jndiSqlSessionFactory.setDataSource(jndiDataSource());
                jndiSqlSessionFactory.setConfigLocation(
                                new PathMatchingResourcePatternResolver()
                                                .getResource("classpath:config/mybatis-config.xml"));
                // mybatis 설정 파일
                jndiSqlSessionFactory.setMapperLocations(
                                new PathMatchingResourcePatternResolver()
                                                .getResources("classpath:config/mapper/*.xml"));
                return jndiSqlSessionFactory;
        }

        // transaction 설정
        @Bean
        PlatformTransactionManager jndiTransactionManager() {
                DataSourceTransactionManager jndiTransactionManager = new DataSourceTransactionManager();
                jndiTransactionManager.setDataSource(jndiDataSource());

                return jndiTransactionManager;
        }
}
