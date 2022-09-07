/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overloading;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig( unmappedTargetPolicy = ReportingPolicy.ERROR,
               mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG )
public interface OverloadingMapperConfig {

    @Mapping( target = "updatedOn", source = "updatedOnTarget" )
    Source targetToSourceReversed(Target source);

}
