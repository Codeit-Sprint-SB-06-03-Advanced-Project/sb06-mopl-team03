package org.codeit.sb06.team03.mopl.common.config;

import io.github.openfeign.querydsl.jpa.spring.repository.config.EnableQuerydslRepositories;
import org.codeit.sb06.team03.mopl.MoplApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableQuerydslRepositories(basePackageClasses = MoplApplication.class)
public class QuerydslConfig {
}
