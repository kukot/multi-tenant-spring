package com.demo.multitenancysetup.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TenantRoutingDatasource extends AbstractRoutingDataSource {
    private final CurrentTenantResolver tenantResolver;

    public TenantRoutingDatasource(CurrentTenantResolver tenantResolver, MultiTenantDatasourceConfig multiTenantDatasourceConfig) {
        this.tenantResolver = tenantResolver;
        setTargetDataSources(multiTenantDatasourceConfig.getMultitenant().stream()
                .collect(Collectors.toMap(DataSourceProperties::id, dsProps -> {
                    var hikariConfig = new HikariConfig();
                    hikariConfig.setInitializationFailTimeout(0);
                    hikariConfig.setMinimumIdle(1);
                    hikariConfig.setMaximumPoolSize(1);
                    hikariConfig.setIdleTimeout(300000);
                    hikariConfig.setMaxLifetime(30000);
                    hikariConfig.setConnectionTimeout(60000);
                    hikariConfig.setDataSourceClassName(dsProps.datasourceClassName());
                    hikariConfig.setJdbcUrl(dsProps.url());
                    hikariConfig.setUsername(dsProps.username());
                    hikariConfig.setPassword(dsProps.password());
                    return new HikariDataSource(hikariConfig);
                })));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return tenantResolver.resolveCurrentTenantIdentifier();
    }

}
