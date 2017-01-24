/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nestedbeans;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nestedbeans.maps.AutoMapMapper;
import org.mapstruct.ap.test.nestedbeans.maps.Bar;
import org.mapstruct.ap.test.nestedbeans.maps.BarDto;
import org.mapstruct.ap.test.nestedbeans.maps.Dto;
import org.mapstruct.ap.test.nestedbeans.maps.Entity;
import org.mapstruct.ap.test.nestedbeans.multiplecollections.Garage;
import org.mapstruct.ap.test.nestedbeans.multiplecollections.GarageDto;
import org.mapstruct.ap.test.nestedbeans.multiplecollections.MultipleListMapper;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.util.Arrays;
import java.util.HashMap;

/**
 * This test is for a case when several identical methods could be generated, what is an easy edge case to miss.
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class MultipleForgedMethodsTest {

    @WithClasses({
        Bar.class, BarDto.class, Dto.class, Entity.class, AutoMapMapper.class
    })
    @Test
    public void testNestedMapsAutoMap() {

        HashMap<BarDto, BarDto> dtoMap = new HashMap<BarDto, BarDto>();
        HashMap<Bar, Bar> entityMap = new HashMap<Bar, Bar>();
        for ( int i = 0; i < 10; i++ ) {
            String key = "key-" + i;
            String value = "value-" + i;
            dtoMap.put( new BarDto( key ), new BarDto( value ) );
            entityMap.put( new Bar( key ), new Bar( value ) );
        }

        Entity mappedEntity = AutoMapMapper.INSTANCE.entityToDto( new Dto( dtoMap ) );

        Assert.assertEquals( "Mapper did not map dto to entity correctly", new Entity( entityMap ), mappedEntity );
    }

    @WithClasses({
        MultipleListMapper.class, Garage.class, GarageDto.class, Car.class, CarDto.class,
        Wheel.class, WheelDto.class
    })
    @Test
    public void testMultipleCollections() {
        GarageDto dto = new GarageDto(
            Arrays.asList( new CarDto(
                "New car",
                2017,
                Arrays.asList( new WheelDto( true, false ), new WheelDto( true, true ) )
            ) ),
            Arrays.asList(
                new CarDto(
                    "Old car-1",
                    1978,
                    Arrays.asList( new WheelDto( false, false ), new WheelDto( false, true ) )
                ),
                new CarDto(
                    "Old car-2",
                    1934,
                    Arrays.asList( new WheelDto( false, true ), new WheelDto( false, false ) )
                )
            )
        );

        Garage entity = new Garage(
            Arrays.asList( new Car(
                "New car",
                2017,
                Arrays.asList( new Wheel( true, false ), new Wheel( true, true ) )
            ) ),
            Arrays.asList(
                new Car( "Old car-1", 1978, Arrays.asList( new Wheel( false, false ), new Wheel( false, true ) ) ),
                new Car( "Old car-2", 1934, Arrays.asList( new Wheel( false, true ), new Wheel( false, false ) ) )
            )
        );

        GarageDto mappedDto = MultipleListMapper.INSTANCE.convert( entity );

        Assert.assertEquals( "Mapper did not map entity to dto correctly", dto, mappedDto );

    }

}
