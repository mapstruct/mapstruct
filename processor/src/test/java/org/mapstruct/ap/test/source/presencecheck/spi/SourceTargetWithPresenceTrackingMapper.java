/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Cindy Wang
 */
@Mapper
public abstract class SourceTargetWithPresenceTrackingMapper {

    public static final SourceTargetWithPresenceTrackingMapper INSTANCE =
        Mappers.getMapper( SourceTargetWithPresenceTrackingMapper.class );

    abstract TargetWithPresenceTracking sourceToTarget(Source source);
}
