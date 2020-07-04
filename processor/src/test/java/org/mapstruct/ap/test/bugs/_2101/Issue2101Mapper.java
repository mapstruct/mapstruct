/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2101;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2101Mapper {

    Issue2101Mapper INSTANCE = Mappers.getMapper( Issue2101Mapper.class );

    @Mapping(target = "value1", source = "codeValue1")
    @Mapping(target = "value2", source = "codeValue2")
    Source map(Target target);

    @InheritInverseConfiguration
    @Mapping(target = "codeValue1.code", constant = "c1")
    @Mapping(target = "codeValue1.value", source = "value1")
    @Mapping(target = "codeValue2.code", constant = "c2")
    @Mapping(target = "codeValue2.value", source = "value2")
    Target map(Source source);

    default String mapFrom( CodeValuePair cv ) {
        return cv.code;
    }

    //CHECKSTYLE:OFF
    class Source {
        public String value1;
        public String value2;
    }

    class Target {
        public CodeValuePair codeValue1;
        public CodeValuePair codeValue2;
    }

    class CodeValuePair {
        public String code;
        public String value;
    }
    //CHECKSTYLE:ON
}
