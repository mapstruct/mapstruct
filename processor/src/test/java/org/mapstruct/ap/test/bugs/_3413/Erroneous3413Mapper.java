/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3413;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Muhammad Usama
 */
@Mapper
public interface Erroneous3413Mapper {
    Erroneous3413Mapper INSTANCE = Mappers.getMapper( Erroneous3413Mapper.class );

    @Mapping(target = "", expression = "", conditionQualifiedByName = "")
    ToPOJO map(FromPOJO fromPOJO);

    class FromPOJO {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class ToPOJO {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
