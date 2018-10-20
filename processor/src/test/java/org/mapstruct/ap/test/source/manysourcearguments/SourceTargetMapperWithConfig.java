/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manysourcearguments;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( config = SourceTargetConfig.class )
public interface SourceTargetMapperWithConfig {

    SourceTargetMapperWithConfig INSTANCE = Mappers.getMapper( SourceTargetMapperWithConfig.class );

    @InheritConfiguration
    DeliveryAddress personAndAddressToDeliveryAddress(Person person, Address address);

}
