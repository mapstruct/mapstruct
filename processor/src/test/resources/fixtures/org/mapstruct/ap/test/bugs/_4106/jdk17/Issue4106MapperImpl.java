/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4106.jdk17;

import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T22:10:29+0530",
    comments = "version: , compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
public class Issue4106MapperImpl implements Issue4106Mapper {

    @Override
    public Issue4106Mapper.TargetDto map(Issue4106Mapper.SourceEntity sourceEntity) {

        OffsetDateTime createdAt = null;
        OffsetDateTime closedAt = null;

        createdAt = toOffsetTime( sourceEntity.createdAt() );
        if ( sourceEntity.closedAt() != null ) {
            closedAt = toOffsetTime( sourceEntity.closedAt() );
        }

        Issue4106Mapper.TargetDto targetDto = new Issue4106Mapper.TargetDto( createdAt, closedAt );

        return targetDto;
    }
}
