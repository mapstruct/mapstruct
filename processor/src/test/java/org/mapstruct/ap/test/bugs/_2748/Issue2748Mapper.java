/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2748;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2748Mapper {

    Issue2748Mapper INSTANCE = Mappers.getMapper( Issue2748Mapper.class );

    @Mapping(target = "specificValue", source = "annotations.specific/value")
    Target map(Source source);

    class Target {
        private final String specificValue;

        public Target(String specificValue) {
            this.specificValue = specificValue;
        }

        public String getSpecificValue() {
            return specificValue;
        }
    }

    class Source {
        private final Map<String, String> annotations;

        public Source(Map<String, String> annotations) {
            this.annotations = annotations;
        }

        public Map<String, String> getAnnotations() {
            return annotations;
        }
    }
}
