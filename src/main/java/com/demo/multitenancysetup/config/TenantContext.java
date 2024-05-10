package com.demo.multitenancysetup.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantContext {
    private TenantContext() {}

    private static InheritableThreadLocal<Object> currentTenant = new InheritableThreadLocal<>();

    public static void setTenantId(Object tenantId) {
        log.debug("Setting tenant id: {}", tenantId);
        currentTenant.set(tenantId);
    }

    public static Object getTenantId() {
        return currentTenant.get();
    }

    public static void clearTenant() {
        currentTenant.remove();
    }
}
