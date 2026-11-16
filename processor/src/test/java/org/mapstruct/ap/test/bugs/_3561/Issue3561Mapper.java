/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3561;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3561Mapper {

    Issue3561Mapper INSTANCE = Mappers.getMapper( Issue3561Mapper.class );

    @Mapping(target = "value", conditionQualifiedByName = "shouldMapValue")
    Target map(Source source, @Context boolean shouldMapValue);

    @Condition
    @Named("shouldMapValue")
    default boolean shouldMapValue(@Context boolean shouldMapValue) {
        return shouldMapValue;
    }

    class Target {

        private final String value;

        public Target(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Source {

        private String value;
        private boolean valueInitialized;

        public String getValue() {
            if ( valueInitialized ) {
                return value;
            }

            throw new IllegalStateException( "value is not initialized" );
        }

        public void setValue(String value) {
            this.valueInitialized = true;
            this.value = value;
        }
    }
}
