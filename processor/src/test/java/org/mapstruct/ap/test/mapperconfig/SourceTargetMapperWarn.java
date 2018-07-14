/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapperconfig;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = { CustomMapperViaMapper.class },
        config = CentralConfig.class,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface SourceTargetMapperWarn {

    SourceTargetMapperWarn INSTANCE = Mappers.getMapper( SourceTargetMapperWarn.class );

    TargetNoFoo toTarget( Source source );
}
