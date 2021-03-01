/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.reverse;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings({
        @Mapping(target = "stringPropY", source = "stringPropX"),
        @Mapping(target = "integerPropY", source = "integerPropX"),
        @Mapping(target = "propertyNotToIgnoreUpstream", source = "propertyToIgnoreDownstream")
    })
    Target forward(Source source);

    @Mappings({
        @Mapping(target = "stringPropY", source = "stringPropX"),
        @Mapping(target = "integerPropY", source = "integerPropX"),
        @Mapping(target = "propertyNotToIgnoreUpstream", source = "propertyToIgnoreDownstream")
    })
    Target forwardNotToReverse(Source source);

    @InheritInverseConfiguration(name = "forward")
    @Mappings({
        @Mapping(target = "someConstantDownstream", constant = "test"),
        @Mapping(target = "propertyToIgnoreDownstream", ignore = true)
    })
    Source reverse(Target target);
}
