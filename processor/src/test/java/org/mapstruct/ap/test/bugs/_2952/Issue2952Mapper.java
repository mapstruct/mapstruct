/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2952;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface Issue2952Mapper {

    Issue2952Mapper INSTANCE = Mappers.getMapper( Issue2952Mapper.class );

    Target map(Source source);

    class Source {
        private final String value;

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Target {
        private final Map<String, String> attributes = new HashMap<>();
        private final List<String> values = new ArrayList<>();
        private String value;

        public Map<String, String> getAttributes() {
            return Collections.unmodifiableMap( attributes );
        }

        public List<String> getValues() {
            return Collections.unmodifiableList( values );
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
