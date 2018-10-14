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

    @Mapping(source = "stringPropX", target = "stringPropY")
    @Mapping(source = "integerPropX", target = "integerPropY")
    @Mapping(source = "propertyToIgnoreDownstream", target = "propertyNotToIgnoreUpstream")
    Target forward(Source source);

    @Mapping(source = "stringPropX", target = "stringPropY")
    @Mapping(source = "integerPropX", target = "integerPropY")
    @Mapping(source = "propertyToIgnoreDownstream", target = "propertyNotToIgnoreUpstream")
    Target forwardNotToReverse(Source source);

    @InheritInverseConfiguration(name = "forward")
    @Mapping(target = "someConstantDownstream", constant = "test")
    @Mapping(target = "propertyToIgnoreDownstream", ignore = true)
    Source reverse(Target target);
}
