/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2164;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2164Mapper {

    Issue2164Mapper INSTANCE = Mappers.getMapper( Issue2164Mapper.class );

    @Mapping(target = "value", qualifiedByName = "truncate2")
    Target map(BigDecimal value);

    @Named( "truncate2" )
    default String truncate2(String in) {
        return in.substring( 0, 2 );
    }

    class Target {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
