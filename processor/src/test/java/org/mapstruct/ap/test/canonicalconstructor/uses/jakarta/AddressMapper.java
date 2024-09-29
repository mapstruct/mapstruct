/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.uses.jakarta;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface AddressMapper {

    AddressDto map(AddressEntity address);

}
