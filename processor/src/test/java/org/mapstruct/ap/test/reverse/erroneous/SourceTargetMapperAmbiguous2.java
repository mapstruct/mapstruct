/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.reverse.erroneous;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.reverse.Source;
import org.mapstruct.ap.test.reverse.Target;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTargetMapperAmbiguous2 {

    SourceTargetMapperAmbiguous2 INSTANCE = Mappers.getMapper( SourceTargetMapperAmbiguous2.class );

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

    @InheritInverseConfiguration(name = "blah")
    @Mappings({
        @Mapping(target = "someConstantDownstream", constant = "test"),
        @Mapping(target = "propertyToIgnoreDownstream", ignore = true)
    })
    Source reverse(Target target);
}
