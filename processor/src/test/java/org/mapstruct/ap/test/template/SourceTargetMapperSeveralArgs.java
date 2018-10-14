/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.template;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTargetMapperSeveralArgs {

    SourceTargetMapperSeveralArgs INSTANCE = Mappers.getMapper( SourceTargetMapperSeveralArgs.class );

    @Mapping( target = "stringPropY", source = "s1.stringPropX" )
    @Mapping( target = "integerPropY", source = "s1.integerPropX" )
    @Mapping( target = "nestedResultProp", source = "s1.nestedSourceProp.nested" )
    Target forwardCreate(Source s1, String constantProp, String expressionProp);

    @InheritConfiguration
    void forwardUpdate(Source source, String constantProp, String expressionProp, @MappingTarget Target target);

}
