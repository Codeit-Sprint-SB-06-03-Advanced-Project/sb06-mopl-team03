package org.codeit.sb06.team03.mopl.common.config;

import org.codeit.sb06.team03.mopl.MoplApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = MoplApplication.class)
public class AppConfig {
}
