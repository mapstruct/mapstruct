/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._581;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._581.source.Car;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    org.mapstruct.ap.test.bugs._581._target.Car[] sourceToTarget( List<Car> source );

    org.mapstruct.ap.test.bugs._581._target.Car sourceToTarget( Car source );
}
