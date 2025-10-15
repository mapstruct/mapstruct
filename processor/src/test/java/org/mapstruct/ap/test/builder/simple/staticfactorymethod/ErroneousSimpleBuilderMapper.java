/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.simple.staticfactorymethod;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.builder.simple.SimpleMutablePerson;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ErroneousSimpleBuilderMapper {

    @Mappings({
        @Mapping(target = "address", ignore = true ),
        @Mapping(target = "job", ignore = true ),
        @Mapping(target = "city", ignore = true )
    })
    SimpleImmutablePersonWithStaticFactoryMethodBuilder toImmutable(SimpleMutablePerson source);
}
