/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.versioninfo;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

/**
 * @author Filip Hrisafov
 */
@Mapper(config = SuppressTimestampViaMapperConfig.Config.class)
public interface SuppressTimestampViaMapperConfig {
    Object toObject(Object object);

    @MapperConfig(suppressGeneratorTimestamp = true)
    interface Config {

    }
}
