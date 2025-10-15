/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1821;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue1821Mapper {

    Issue1821Mapper INSTANCE = Mappers.getMapper( Issue1821Mapper.class );

    @BeanMapping( resultType = Target.class )
    Target map(Source source);

    @InheritConfiguration( name = "map" )
    ExtendedTarget mapExtended(Source source);

    class Target {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class ExtendedTarget extends Target {
    }

    class Source {

        private final String value;

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
