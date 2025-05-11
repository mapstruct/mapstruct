/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1460.java8;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Bandowski
 */
@Mapper
public abstract class Issue1460JavaTimeMapper {

    public static final Issue1460JavaTimeMapper INSTANCE = Mappers.getMapper( Issue1460JavaTimeMapper.class );

    public abstract Target map(Source source);

    public abstract LocalTarget forceUsageOfLocalDate(LocalDate source);

    public static class LocalDate {
    }

    public static class LocalTarget {

        private final LocalDate source;

        public LocalTarget(LocalDate source) {
            this.source = source;
        }
    }
}
