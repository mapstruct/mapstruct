/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2421;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper
abstract class Issue2421Mapper {

    @Mapping( target = ".", source = "value" )
    @ValueMapping( target = "C", source = MappingConstants.ANY_REMAINING )
    public abstract EnumExample dtoTestToEnum(InnerDtoTest innerDtoTest);

}

enum EnumExample {
    A, B, C
}

class InnerDtoTest {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class TargetTest {
    private EnumExample enumExampleValue;

    public EnumExample getEnumExampleValue() {
        return enumExampleValue;
    }

    public void setEnumExampleValue(EnumExample enumExampleValue) {
        this.enumExampleValue = enumExampleValue;
    }
}
