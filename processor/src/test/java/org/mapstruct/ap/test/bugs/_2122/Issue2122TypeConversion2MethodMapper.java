/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface Issue2122TypeConversion2MethodMapper {

    Issue2122TypeConversion2MethodMapper INSTANCE = Mappers.getMapper( Issue2122TypeConversion2MethodMapper.class );

    @Mapping(target = "strings", source = "value")
    Target toTarget(Source source);

    default <T> List<T> singleEntry(T entry) {
        return Collections.singletonList( entry );
    }

    class Source {
        Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    class Target {
        List<String> strings;

        public List<String> getStrings() {
            return strings;
        }

        public void setStrings(List<String> strings) {
            this.strings = strings;
        }
    }

}
