/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3385;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Mapper
public interface Issue3385Mapper {

    Target map(Source source);

    @AfterMapping
    @SuppressWarnings( "unused" )
    default void used(Source source) {
    }

    @AfterMapping
    @SuppressWarnings( "unused" )
    default void unused(String notAMappingParameter, Source source) {
    }

    @BeforeMapping
    @SuppressWarnings( "unused" )
    default void unusedBefore(String notAMappingParameter, Source source) {
    }

    class Source {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Target {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
