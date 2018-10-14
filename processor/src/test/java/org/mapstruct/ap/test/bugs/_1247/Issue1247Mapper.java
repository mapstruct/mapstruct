/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1247;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1247Mapper {

    Issue1247Mapper INSTANCE = Mappers.getMapper( Issue1247Mapper.class );

    @Mapping(target = "internal", source = "in")
    @Mapping(target = "internal.internalData.list", source = "list")
    DtoOut map(DtoIn in, List<String> list);

    @Mapping(target = "internal", source = "in")
    @Mapping(target = "internal.expression", expression = "java(\"testingExpression\")")
    @Mapping(target = "internal.internalData.list", source = "list")
    @Mapping(target = "internal.internalData.defaultValue", source = "in.data2", defaultValue = "missing")
    @Mapping(target = "constant", constant = "someConstant")
    OtherDtoOut mapWithConstantExpressionAndDefault(DtoIn in, List<String> list);

}
