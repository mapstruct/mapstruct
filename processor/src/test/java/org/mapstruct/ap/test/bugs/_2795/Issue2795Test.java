/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795;

import org.mapstruct.ap.test.bugs._2795.dto.CarDto;
import org.mapstruct.ap.test.bugs._2795.dto.PlaneDto;
import org.mapstruct.ap.test.bugs._2795.dto.ShipDto;
import org.mapstruct.ap.test.bugs._2795.dto.TripDto;
import org.mapstruct.ap.test.bugs._2795.mapper.CarMapper;
import org.mapstruct.ap.test.bugs._2795.mapper.MapperConfig;
import org.mapstruct.ap.test.bugs._2795.mapper.PlaneMapper;
import org.mapstruct.ap.test.bugs._2795.mapper.ShipMapper;
import org.mapstruct.ap.test.bugs._2795.mapper.TripMapper;
import org.mapstruct.ap.test.bugs._2795.model.Car;
import org.mapstruct.ap.test.bugs._2795.model.Plane;
import org.mapstruct.ap.test.bugs._2795.model.Ship;
import org.mapstruct.ap.test.bugs._2795.model.Trip;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey( "2795" )
@WithClasses( { CarDto.class, PlaneDto.class, ShipDto.class, TripDto.class, Car.class, Plane.class, Ship.class,
    Trip.class, CarMapper.class, PlaneMapper.class, ShipMapper.class, TripMapper.class, MapperConfig.class } )
public class Issue2795Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
