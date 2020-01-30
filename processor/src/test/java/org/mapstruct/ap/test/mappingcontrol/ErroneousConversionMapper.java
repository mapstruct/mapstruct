/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.mapstruct.Mapper;
import org.mapstruct.control.AllowDirect;
import org.mapstruct.factory.Mappers;

@Mapper(mappingControl = AllowDirect.class)
public interface ErroneousConversionMapper {

    ErroneousConversionMapper INSTANCE = Mappers.getMapper( ErroneousConversionMapper.class );

    Fridge map(CoolBeerDTO in);

}
