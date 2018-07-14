/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java;

import java.util.Arrays;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( imports = { Arrays.class } )
public interface SourceTargetListMapper {

    SourceTargetListMapper INSTANCE = Mappers.getMapper( SourceTargetListMapper.class );

    @Mapping(target = "list", expression = "java(Arrays.asList( \"test2\" ))")
    TargetList map( SourceList source );
}
