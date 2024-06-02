/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3601;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SourceParameterCondition;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3601Mapper {

    Issue3601Mapper INSTANCE = Mappers.getMapper( Issue3601Mapper.class );

    @Mapping(target = "currentId", source = "source.uuid")
    @Mapping(target = "targetIds", source = "sourceIds")
    Target map(Source source, List<String> sourceIds);

    @SourceParameterCondition
    default boolean isNotEmpty(List<String> elements) {
        return elements != null && !elements.isEmpty();
    }

    class Source {
        private final String uuid;

        public Source(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }

    class Target {
        //CHECKSTYLE:OFF
        public String currentId;
        public List<String> targetIds;
        //CHECKSTYLE:ON
    }
}
