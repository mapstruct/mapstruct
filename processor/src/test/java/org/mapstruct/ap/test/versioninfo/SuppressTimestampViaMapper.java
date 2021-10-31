/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.versioninfo;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(suppressTimestampInGenerated = true)
public interface SuppressTimestampViaMapper {
    Object toObject(Object object);
}
