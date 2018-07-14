/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface BooleanWorkAroundMapper {

    BooleanWorkAroundMapper INSTANCE = Mappers.getMapper( BooleanWorkAroundMapper.class );

    @Mapping( expression = "java(source.isVal())", target = "val" )
    TargetBooleanWorkAround mapST( SourceBooleanWorkAround source );

    SourceBooleanWorkAround mapTS( TargetBooleanWorkAround target );
}
