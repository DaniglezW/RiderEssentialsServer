package com.rider.essentials.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@AutoConfiguration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@AutoConfigureBefore(HibernateJpaAutoConfiguration.class)
public class DatabaseMigrationAutoConfiguration {

    @Bean
    public DatabaseMigrationRunner databaseMigrationRunner(DataSource dataSource) {
        DatabaseMigrationRunner runner = new DatabaseMigrationRunner(new JdbcTemplate(dataSource));
        runner.runMigrations();
        return runner;
    }

    @Bean
    static BeanFactoryPostProcessor entityManagerFactoryDependsOnMigration() {
        return beanFactory -> {
            if (beanFactory.containsBeanDefinition("entityManagerFactory")) {
                var definition = beanFactory.getBeanDefinition("entityManagerFactory");
                definition.setDependsOn(mergeDependsOn(definition.getDependsOn(), "databaseMigrationRunner"));
            }
        };
    }

    private static String[] mergeDependsOn(String[] existing, String additional) {
        if (existing == null || existing.length == 0) {
            return new String[]{additional};
        }
        for (String dep : existing) {
            if (additional.equals(dep)) {
                return existing;
            }
        }
        String[] merged = new String[existing.length + 1];
        System.arraycopy(existing, 0, merged, 0, existing.length);
        merged[existing.length] = additional;
        return merged;
    }
}
