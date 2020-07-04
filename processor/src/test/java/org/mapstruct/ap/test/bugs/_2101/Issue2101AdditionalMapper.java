/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2101;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2101AdditionalMapper {

    Issue2101AdditionalMapper INSTANCE = Mappers.getMapper( Issue2101AdditionalMapper.class );

    @Mapping(target = "value1", source = "value.nestedValue1")
    @Mapping(target = "value2", source = "value.nestedValue2")
    @Mapping(target = "value3", source = "valueThrowOffPath")
    Target map1(Source source);

    @InheritConfiguration
    @Mapping(target = "value2", source = "value.nestedValue1")
    @Mapping(target = "value3", constant = "test")
    Target map2(Source source);

    //CHECKSTYLE:OFF
    class Source {
        public String valueThrowOffPath;
        public NestedSource value;
    }

    class Target {
        public String value1;
        public String value2;
        public String value3;
    }

    class NestedSource {
        public String nestedValue1;
        public String nestedValue2;
    }
    //CHECKSTYLE:ON
}
