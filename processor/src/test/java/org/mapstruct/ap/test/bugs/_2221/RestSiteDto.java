/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2221;

/**
 * @author Filip Hrisafov
 */
public class RestSiteDto {

    private final String tenantId;
    private final String siteId;
    private final String cti;

    public RestSiteDto(String tenantId, String siteId, String cti) {
        this.tenantId = tenantId;
        this.siteId = siteId;
        this.cti = cti;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getCti() {
        return cti;
    }
}
