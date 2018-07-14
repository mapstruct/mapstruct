/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._955;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.bugs._955.dto.Car;
import org.mapstruct.ap.test.bugs._955.dto.SuperCar;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = {CustomMapper.class})
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    SuperCar mapCar(Car source, @MappingTarget SuperCar destination);
}
