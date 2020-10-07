/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2221;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@MapperConfig
public interface RestConfig {

    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "siteId", source = "siteId")
    @Mapping(target = "ctiId", source = "source.cti", defaultValue = "unknown")
    SiteDto convert(RestSiteDto source, String tenantId, String siteId);
}
