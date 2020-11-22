/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2221;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(config = RestConfig.class)
public interface RestSiteMapper {

    RestSiteMapper INSTANCE = Mappers.getMapper( RestSiteMapper.class );

    @InheritConfiguration
    SiteDto convert(RestSiteDto source, String tenantId, String siteId);
}
