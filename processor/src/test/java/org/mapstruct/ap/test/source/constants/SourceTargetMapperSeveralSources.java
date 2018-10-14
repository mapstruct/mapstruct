/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTargetMapperSeveralSources {

    SourceTargetMapperSeveralSources INSTANCE = Mappers.getMapper( SourceTargetMapperSeveralSources.class );

    @Mapping(source = "s1.someProp", target = "someProp" )
    @Mapping(source = "s2.anotherProp", target = "anotherProp" )
    @Mapping(target = "someConstant", constant = "stringConstant")
    Target2 sourceToTarget(Source1 s1, Source2 s2);
}
