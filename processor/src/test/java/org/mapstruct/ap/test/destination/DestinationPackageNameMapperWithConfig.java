/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christophe Labouisse on 27/05/2015.
 */
@Mapper(config = DestinationPackageNameMapperConfig.class)
public interface DestinationPackageNameMapperWithConfig {
    DestinationPackageNameMapperWithConfig INSTANCE = Mappers.getMapper( DestinationPackageNameMapperWithConfig.class );

    Target map(Integer source);
}
