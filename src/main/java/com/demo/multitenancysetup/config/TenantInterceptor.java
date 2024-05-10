package com.demo.multitenancysetup.config;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Component
public class TenantInterceptor implements WebRequestInterceptor {
    @Override
    public void preHandle(WebRequest request) {
        String tenantId = request.getHeader("X-TENANT-ID");
        TenantContext.setTenantId(tenantId);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        TenantContext.clearTenant();
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    }
}
