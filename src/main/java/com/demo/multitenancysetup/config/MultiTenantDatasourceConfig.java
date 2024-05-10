package com.demo.multitenancysetup.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties
@Data
public class MultiTenantDatasourceConfig {
    private List<DataSourceProperties> multitenant;
}

record DataSourceProperties(String id, String url, String username, String password, String datasourceClassName) {}
