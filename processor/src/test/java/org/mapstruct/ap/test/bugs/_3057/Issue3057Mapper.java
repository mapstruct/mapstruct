/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3057;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Issue3057Mapper {

    Issue3057Mapper INSTANCE = Mappers.getMapper( Issue3057Mapper.class );

    class Source {
        private Source self;

        public Source getSelf() {
            return self;
        }

        public void setSelf(Source self) {
            this.self = self;
        }
    }

    class Target {
        private Target self;
        private String value;

        public Target getSelf() {
            return self;
        }

        public void setSelf(Target self) {
            this.self = self;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Mapping( target = "value", constant = "constantValue" )
    Target map(Source source);
}
