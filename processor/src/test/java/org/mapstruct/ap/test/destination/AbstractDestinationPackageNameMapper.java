/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(implementationPackage = "<PACKAGE_NAME>.dest")
public abstract class AbstractDestinationPackageNameMapper {
    public static final AbstractDestinationPackageNameMapper INSTANCE =
        Mappers.getMapper( AbstractDestinationPackageNameMapper.class );

    public abstract Target map(Integer source);
}
