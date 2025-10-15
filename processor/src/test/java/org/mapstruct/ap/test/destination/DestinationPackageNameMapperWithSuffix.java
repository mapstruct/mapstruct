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
@Mapper(implementationName = "<CLASS_NAME>MyImpl", implementationPackage = "<PACKAGE_NAME>.dest")
public interface DestinationPackageNameMapperWithSuffix {
    DestinationPackageNameMapperWithSuffix INSTANCE = Mappers.getMapper( DestinationPackageNameMapperWithSuffix.class );

    Target map(Integer source);
}
