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
@Mapper(implementationPackage = "<PACKAGE_NAME>.dest")
public interface DestinationPackageNameMapper {
    DestinationPackageNameMapper INSTANCE = Mappers.getMapper( DestinationPackageNameMapper.class );

    Target map(Integer source);
}
