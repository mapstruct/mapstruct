/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface Issue2122Method2TypeConversionMapper {

    Issue2122Method2TypeConversionMapper INSTANCE = Mappers.getMapper( Issue2122Method2TypeConversionMapper.class );

    @Mapping(target = "value", source = "strings")
    Target toTarget(Source source);

    default <T> T toFirstElement(List<T> entry) {
        return entry.get( 0 );
    }

    class Source {
        List<String> strings;

        public List<String> getStrings() {
            return strings;
        }

        public void setStrings(List<String> strings) {
            this.strings = strings;
        }
    }

    class Target {
        Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

}
