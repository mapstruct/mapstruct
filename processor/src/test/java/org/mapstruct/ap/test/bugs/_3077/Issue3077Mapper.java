/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3077;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3077Mapper {

    Issue3077Mapper INSTANCE = Mappers.getMapper( Issue3077Mapper.class );

    class Source {
        private final String source;
        private final Source self;

        public Source(String source, Source self) {
            this.source = source;
            this.self = self;
        }

        public String getSource() {
            return source;
        }

        public Source getSelf() {
            return self;
        }
    }

    class Target {
        private String value;
        private Target self;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Target getSelf() {
            return self;
        }

        public void setSelf(Target self) {
            this.self = self;
        }

    }

    @Named("self")
    @Mapping(target = "value", source = "source")
    @Mapping(target = "self", qualifiedByName = "self")
    Target map(Source source);
}
